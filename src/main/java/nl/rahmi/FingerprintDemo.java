package nl.rahmi;

import com.machinezoo.sourceafis.FingerprintImage;
import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;

import java.nio.file.Files;
import java.nio.file.Path;

public class FingerprintDemo {
    public static void main(String[] args) {
        try {
            System.out.println("Starting fingerprint comparison...");

            // Test 1: gebruik twee verschillende files
            byte[] image1 = Files.readAllBytes(Path.of("finger1.png"));
            byte[] image2 = Files.readAllBytes(Path.of("finger2.png"));

            // Test 2: gebruik dezelfde file tegen zichzelf
            // Zet deze aan als je wilt testen of je afbeelding überhaupt werkt:
            // byte[] image1 = Files.readAllBytes(Path.of("finger1.png"));
            // byte[] image2 = Files.readAllBytes(Path.of("finger1.png"));

            System.out.println("Images loaded");

            FingerprintTemplate template1 = new FingerprintTemplate(
                    new FingerprintImage(image1)
            );

            System.out.println("Template 1 created");

            FingerprintTemplate template2 = new FingerprintTemplate(
                    new FingerprintImage(image2)
            );

            System.out.println("Template 2 created");

            System.out.println("Template 1 size: " + template1.toByteArray().length);
            System.out.println("Template 2 size: " + template2.toByteArray().length);

            double score = new FingerprintMatcher(template1).match(template2);

            System.out.printf("Score: %.20f%n", score);

            if (score >= 40) {
                System.out.println("MATCH");
            } else {
                System.out.println("GEEN MATCH");
            }

        } catch (Exception e) {
            System.out.println("Er ging iets fout:");
            e.printStackTrace();
        }
    }
}