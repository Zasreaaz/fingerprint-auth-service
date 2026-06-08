package nl.rahmi;

import com.machinezoo.sourceafis.FingerprintImage;
import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;

import java.nio.file.Files;
import java.nio.file.Path;

public class FingerprintDemo {
    public static void main(String[] args) throws Exception {
        byte[] image1 = Files.readAllBytes(Path.of("finger1.png"));
        byte[] image2 = Files.readAllBytes(Path.of("finger2.png"));

        FingerprintTemplate template1 = new FingerprintTemplate(
                new FingerprintImage(image1)
        );

        FingerprintTemplate template2 = new FingerprintTemplate(
                new FingerprintImage(image2)
        );

        double score = new FingerprintMatcher(template1).match(template2);

        System.out.println("Score: " + score);
        System.out.println(score >= 40 ? "MATCH" : "GEEN MATCH");
    }
}