package org.example.lab5.task4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

public class TagCounter {
    public Map<String, Integer> count(String url) throws IOException {
        Map<String, Integer> counts = new HashMap<>();
        URI uri = URI.create(url);
        if (!"http".equalsIgnoreCase(uri.getScheme()) && !"https".equalsIgnoreCase(uri.getScheme())) {
            throw new IllegalArgumentException("URL повинен починатися з http:// або https://.");
        }
        URLConnection connection = uri.toURL().openConnection();
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        Charset charset = StandardCharsets.UTF_8;
        String contentType = connection.getContentType();
        if (contentType != null) {
            Matcher matcher = Pattern.compile("charset\\s*=\\s*[\"']?([^\\s;\"']+)",
                    Pattern.CASE_INSENSITIVE).matcher(contentType);
            if (matcher.find()) {
                charset = Charset.forName(matcher.group(1));
            }
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), charset))) {
            new ParserDelegator().parse(reader, new HTMLEditorKit.ParserCallback() {
                @Override
                public void handleStartTag(HTML.Tag tag, MutableAttributeSet attributes, int position) {
                    countTag(tag, attributes);
                }

                @Override
                public void handleSimpleTag(HTML.Tag tag, MutableAttributeSet attributes, int position) {
                    countTag(tag, attributes);
                }

                private void countTag(HTML.Tag tag, MutableAttributeSet attributes) {
                    if (!attributes.isDefined(IMPLIED) && !attributes.isDefined(HTML.Attribute.ENDTAG)) {
                        counts.merge(tag.toString().toLowerCase(Locale.ROOT), 1, Integer::sum);
                    }
                }
            }, true);
        }
        return counts;
    }
}
