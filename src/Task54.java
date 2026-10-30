import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

public class Task54 {
    public static Map<String, Integer> countTags(String address) throws IOException {
        URI uri = URI.create(address);
        if (!"http".equalsIgnoreCase(uri.getScheme())
                && !"https".equalsIgnoreCase(uri.getScheme())) {
            throw new IllegalArgumentException("URL повинен починатися з http:// або https://.");
        }

        URLConnection connection = uri.toURL().openConnection();
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);
        Charset charset = StandardCharsets.UTF_8;
        String contentType = connection.getContentType();
        if (contentType != null) {
            Matcher matcher = Pattern.compile("charset\\s*=\\s*[\"']?([^\\s;\"']+)",
                    Pattern.CASE_INSENSITIVE).matcher(contentType);
            if (matcher.find()) {
                charset = Charset.forName(matcher.group(1));
            }
        }

        try (Reader reader = new InputStreamReader(connection.getInputStream(), charset)) {
            return countTags(reader);
        }
    }

    public static Map<String, Integer> countTags(Reader reader) throws IOException {
        Map<String, Integer> counts = new TreeMap<>();
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
                // Не враховуємо закривальні теги та теги, додані самим парсером.
                if (!attributes.isDefined(IMPLIED) && !attributes.isDefined(HTML.Attribute.ENDTAG)) {
                    String name = tag.toString().toLowerCase(Locale.ROOT);
                    counts.merge(name, 1, Integer::sum);
                }
            }
        }, true);
        return counts;
    }

    public static void printCounts(Map<String, Integer> counts) {
        System.out.println("\nВідкривальні та одиночні теги в лексикографічному порядку:");
        for (Map.Entry<String, Integer> entry : new TreeMap<>(counts).entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        List<Map.Entry<String, Integer>> entries = new ArrayList<>(counts.entrySet());
        entries.sort(Map.Entry.<String, Integer>comparingByValue()
                .thenComparing(Map.Entry.comparingByKey()));
        System.out.println("\nТеги за зростанням частоти:");
        for (Map.Entry<String, Integer> entry : entries) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
