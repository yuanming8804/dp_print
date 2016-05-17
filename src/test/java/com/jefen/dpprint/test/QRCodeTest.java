package com.jefen.dpprint.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

public class QRCodeTest {
	@Test
	public void testEncode() throws WriterException, IOException {
		String filePath = "E://";
		String fileName = "zxing.png";
		JSONObject json = new JSONObject();
		json.put("zxing", "https://github.com/zxing/zxing/tree/zxing-3.0.0/javase/src/main/java/com/google/zxing");
		json.put("author", "shiky");
		String content = json.toJSONString();
		int width = 200;
		int height = 200;
		String format = "png";
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content, 
				BarcodeFormat.QR_CODE, width, height, hints);
		Path path = FileSystems.getDefault().getPath(filePath, fileName);
		MatrixToImageWriter.writeToPath(bitMatrix, format, path); 
	}

	@Test  
    public void testDecode() {  
        String filePath = "E://zxing.png";
        BufferedImage image;
        try {
            image = ImageIO.read(new File(filePath));
            LuminanceSource source = new BufferedImageLuminanceSource(image);  
            Binarizer binarizer = new HybridBinarizer(source);  
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);  
            Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();  
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");  
            Result result = new MultiFormatReader().decode(binaryBitmap, hints);
            JSONObject content = JSONObject.parseObject(result.getText());  
            System.out.println("author" + content.getString("author"));  
            System.out.println("zxing" + content.getString("zxing"));  
            System.out.println("encode" + result.getBarcodeFormat());  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (NotFoundException e) {  
            e.printStackTrace();  
        }  
    }  
}
