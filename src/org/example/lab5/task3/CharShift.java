package org.example.lab5.task3;

final class CharShift {
    private CharShift() {
    }

    static int shift(int character, int shift) {
        int position = character < 0xD800 ? character : character - 0x800;
        int shifted = Math.floorMod(position + shift, 0x10F800);
        return shifted < 0xD800 ? shifted : shifted + 0x800;
    }
}
