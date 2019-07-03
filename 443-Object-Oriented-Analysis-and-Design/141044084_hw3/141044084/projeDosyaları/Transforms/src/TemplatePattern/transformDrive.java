package TemplatePattern;

import java.io.IOException;

public class transformDrive {
    public static void main(String[] args) throws IOException {
        Transform transform = new DFT();
        transform.setOption(true);
        transform.translate();
    }
}