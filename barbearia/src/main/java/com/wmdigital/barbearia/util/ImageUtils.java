package com.wmdigital.barbearia.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

public class ImageUtils {
	
	 public static BufferedImage readImage(InputStream in) throws IOException {
	        return ImageIO.read(in);
	    }

	    /** Redimensiona mantendo proporção. maxDim limita o maior lado (largura/altura). */
	    public static BufferedImage resizeKeepingRatio(BufferedImage src, int maxDim) {
	        int w = src.getWidth();
	        int h = src.getHeight();
	        if (w <= maxDim && h <= maxDim) return src;

	        double scale = (w >= h) ? (maxDim / (double) w) : (maxDim / (double) h);
	        int nw = Math.max(1, (int) Math.round(w * scale));
	        int nh = Math.max(1, (int) Math.round(h * scale));

	        BufferedImage dst = new BufferedImage(nw, nh, BufferedImage.TYPE_INT_RGB);
	        Graphics2D g = dst.createGraphics();
	        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	        // fundo branco para evitar fundo preto quando há transparência
	        g.setColor(Color.WHITE);
	        g.fillRect(0, 0, nw, nh);

	        g.drawImage(src, 0, 0, nw, nh, null);
	        g.dispose();
	        return dst;
	    }

	    /** Garante BufferedImage RGB (sem alpha). */
	    public static BufferedImage ensureRGB(BufferedImage src) {
	        if (src.getType() == BufferedImage.TYPE_INT_RGB) return src;
	        BufferedImage rgb = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB);
	        Graphics2D g = rgb.createGraphics();
	        g.setColor(Color.WHITE);
	        g.fillRect(0, 0, src.getWidth(), src.getHeight());
	        g.drawImage(src, 0, 0, null);
	        g.dispose();
	        return rgb;
	    }

	    /** Escreve JPEG com fator de compressão (0.0–1.0). */
	    public static void writeJpeg(BufferedImage img, Path out, float quality) throws IOException {
	        Files.createDirectories(out.getParent());
	        try (OutputStream os = Files.newOutputStream(out);
	             ImageOutputStream ios = javax.imageio.ImageIO.createImageOutputStream(os)) {

	            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
	            if (!writers.hasNext()) throw new IllegalStateException("Writer JPEG não encontrado.");
	            ImageWriter writer = writers.next();

	            writer.setOutput(ios);
	            ImageWriteParam iwParam = writer.getDefaultWriteParam();
	            if (iwParam.canWriteCompressed()) {
	                iwParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
	                iwParam.setCompressionQuality(quality);
	            }

	            writer.write(null, new IIOImage(img, null, null), iwParam);
	            writer.dispose();
	        }
	    }

}
