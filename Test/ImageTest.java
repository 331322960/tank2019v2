import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ImageTest {
    @Test
    public void testLoadImage(){
        try {
            //只能读绝对路径
            BufferedImage image = ImageIO.read(new File("E:\\JAVA\\Tank19\\src\\images\\GoodTank1.png"));
            assertNotNull(image);

            //利用反射读相对路径
            BufferedImage image2 = ImageIO.read(ImageTest.class.getClassLoader().getResourceAsStream("images/GoodTank1.png"));
            assertNotNull(image2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRotateImage() {
        try {
            BufferedImage tankL = ImageIO.read(ImageTest.class.getClassLoader().getResourceAsStream("images/GoodTank1.png"));
            tankL = rotateImage(tankL, 90);
            assertNotNull(tankL);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public BufferedImage rotateImage(final BufferedImage bufferedimage, final int degree) {
        int w = bufferedimage.getWidth();
        int h = bufferedimage.getHeight();
        int type = bufferedimage.getColorModel().getTransparency();
        BufferedImage img;
        Graphics2D graphics2d;
        (graphics2d = (img = new BufferedImage(w, h, type))
                .createGraphics()).setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2d.rotate(Math.toRadians(degree), w / 2, h / 2);
        graphics2d.drawImage(bufferedimage, 0, 0, null);
        graphics2d.dispose();
        return img;
    }
}
