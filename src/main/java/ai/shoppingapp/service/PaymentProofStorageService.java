package ai.shoppingapp.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PaymentProofStorageService {

	private final Path uploadPath = Paths.get("uploads/payment-proofs");

	private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

	public String save(MultipartFile file) {
		try {
			if (file == null || file.isEmpty()) {
                throw new RuntimeException(
                        "Payment proof is required."
                );
            }
			// File size
            if (file.getSize() > MAX_FILE_SIZE) {
                throw new RuntimeException(
                        "Payment proof must be smaller than 5 MB."
                );
            }
            //File Type
            String contentType = file.getContentType();

            if (contentType == null ||
            		!contentType.equals("image/jpeg") &&
                !contentType.equals("image/png") &&
                !contentType.equals("image/webp")) {

                throw new RuntimeException(
                        "Only JPG, PNG, and WEBP images are allowed."
                );
            }

            Files.createDirectories(uploadPath);

            String originalName =
                    file.getOriginalFilename();

            String extension = "";

            if (originalName != null &&
                    originalName.contains(".")) {

                extension = originalName.substring(
                        originalName.lastIndexOf(".")
                );
            }

            String fileName =
                    UUID.randomUUID() + extension;

            Path target =
                    uploadPath.resolve(fileName);

            Files.copy(
                    file.getInputStream(),
                    target,
                    StandardCopyOption.REPLACE_EXISTING
            );

            return "payment-proofs/" + fileName;

		} catch (IOException e) {
			throw new RuntimeException("Failed to save payment proof.", e);
		}
	}
}
