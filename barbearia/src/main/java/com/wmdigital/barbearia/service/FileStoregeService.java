package com.wmdigital.barbearia.service;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.wmdigital.barbearia.util.ImageUtils;
import com.wmdigital.barbearia.util.StorageDomain;

@Service
public class FileStoregeService  {
	
	private final Path baseUploads;
	
	public FileStoregeService(@Value("${storage.base-dir:uploads}") String baseDir) {
		
		this.baseUploads = Paths.get(System.getProperty("user.dir"), baseDir).toAbsolutePath().normalize();
		
	}
	
	public String saveEntityPhoto(StorageDomain domain, UUID id, MultipartFile file) throws IOException {
	    if (file == null || file.isEmpty()) {
	        throw new IllegalArgumentException("Ficheiro de imagem vazio." + domain);
	    }

	    Files.createDirectories(baseUploads);

	    Path dir = baseUploads.resolve(Paths.get(domain.folder(), id.toString()));
	    Files.createDirectories(dir);

	    try (InputStream in = file.getInputStream()) {
	        BufferedImage original = ImageUtils.readImage(in);
	        if (original == null) {
	            throw new IOException("Não foi possível ler a imagem enviada.");
	        }

	        // cria versões otimizadas
	        BufferedImage main = ImageUtils.resizeKeepingRatio(original, 1200);
	        BufferedImage thumb = ImageUtils.resizeKeepingRatio(original, 320);

	        // força RGB (remove alpha) para JPEG com fundo branco
	        main = ImageUtils.ensureRGB(main);
	        thumb = ImageUtils.ensureRGB(thumb);

	        Path mainPath = dir.resolve("main.jpg");
	        Path thumbPath = dir.resolve("thumb.jpg");

	        ImageUtils.writeJpeg(main, mainPath, 0.85f);
	        ImageUtils.writeJpeg(thumb, thumbPath, 0.70f);
	        
	        if (!Files.exists(mainPath))  throw new IOException("main.jpg não foi criado: "  + mainPath.toAbsolutePath());
	        if (!Files.exists(thumbPath)) throw new IOException("thumb.jpg não foi criado: " + thumbPath.toAbsolutePath());

	        // URL pública (mapeada por StaticResourceConfig)
	        return "files/" + domain.folder() + "/" + id.toString() + "/main.jpg";
	    }
	    }

	public void deleteEntityFolder(StorageDomain domain, UUID id) throws IOException {
	    Path dir = baseUploads.resolve(Paths.get(domain.folder(), id.toString()));
	    if (Files.exists(dir)) {
	        try (var paths = Files.walk(dir)) {
	            paths
	                .sorted((p1, p2) -> p2.getNameCount() - p1.getNameCount())
	                .forEach(p -> {
	                    try {
	                        Files.deleteIfExists(p);
	                    } catch (IOException ignored) {
	                        // opcional: logar o erro
	                    }
	                });
	        }
	    }
	}
}
