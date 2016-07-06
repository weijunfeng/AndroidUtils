package weijunfeng.com.androidutils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hexin on 2016/6/30.
 */
public class QrCodeUtil {
    public static Bitmap creaQrCode(String content, int qrWidth, int qrHeight) {
        return createQrCode(content, "utf-8", Color.BLACK, qrWidth, qrHeight, ErrorCorrectionLevel.H, null);
    }

    public static Bitmap createQrCode(String content, String characterSet, int qrColor, int qrWidth, int qrHeight
            , ErrorCorrectionLevel ecl, Bitmap logo) {
        if (content == null) {
            return null;
        }
        if (characterSet == null) {
            characterSet = "utf-8";
        }
        if (ecl == null) {
            ecl = ErrorCorrectionLevel.H;
        }
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, characterSet);
        hints.put(EncodeHintType.ERROR_CORRECTION, ecl);
        hints.put(EncodeHintType.MARGIN, 0);
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, qrWidth, qrHeight, hints);
            int[] piexs = new int[qrWidth * qrHeight];
            for (int y = 0; y < qrHeight; y++) {
                for (int x = 0; x < qrWidth; x++) {
                    if (bitMatrix.get(x, y)) {
                        piexs[y * qrWidth + x] = qrColor;
                    } else {
                        piexs[y * qrWidth] = 0xffffffff;
                    }
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(qrWidth, qrHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(piexs, 0, qrWidth, 0, 0, qrWidth, qrHeight, true, null);
            if (logo != null) {
                int logoHeight = qrHeight / 5;
                int logoWidth = qrWidth / 5;
                canvas.drawBitmap(genThumbnails(logo, logoWidth, logoHeight), (qrWidth - logoWidth) / 2.0f, (qrHeight - logoHeight) / 2.0f, null);
            }
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Bitmap genThumbnails(Bitmap logo, int logoWidth, int logoHeight) {
        if (logo == null || logoHeight < 0 || logoWidth < 0) {
            throw new IllegalArgumentException();
        }
        Bitmap bitmap = Bitmap.createBitmap(logoWidth, logoHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(Bitmap.createScaledBitmap(logo, logoWidth - 10, logoHeight - 10, false), 5, 5, null);
        return bitmap;
    }
}
