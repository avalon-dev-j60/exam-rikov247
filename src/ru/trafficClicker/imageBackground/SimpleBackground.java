package ru.trafficClicker.imageBackground;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import static ru.trafficClicker.imageBackground.getScaledDownInstance.getScaledDownInstance;
import static ru.trafficClicker.imageBackground.getScaledUpInstance.getScaledUpInstance;

// Даный класс помещает на фон панели картинку и масштабирует ее согласно размерам панели               
public class SimpleBackground extends JPanel {

    private BufferedImage img; // исходное изображение
    private BufferedImage scaled; // изображение с измененными размерами, подстроенными под размеры панели

    // Пустой конструктор
    public SimpleBackground() {
    }

    // Конструктор, который принимает layoutManager. Если это MigLayout, то:
    // - после добавления каждого 4 элемента, следующий добавляется на новую строку;
    // - настраиваем отступы между компонентами.
    public SimpleBackground(LayoutManager layout) {
        setLayout(layout);
        if (layout instanceof MigLayout) {
            ((MigLayout) layout).setLayoutConstraints("wrap 4");
            ((MigLayout) layout).setColumnConstraints("0[]0[]0[]0[]0"); // отступы между строками
            ((MigLayout) layout).setRowConstraints("0[]0[]0[]0[]0"); // отступы между столбцами
        }
    }

    /**
     * Возвращаем предпотительный размер панели. Если загруженное изображение
     * null, то: просто возвращаем предпочтительные размеры панели. Если
     * изображение загружено, то предпочтительные размеры панели будут
     * установленые такими же, как и картинки.
     */
    @Override
    public Dimension getPreferredSize() {
        return img == null ? super.getPreferredSize() : new Dimension(img.getWidth(), img.getHeight());
    }

    // Основной метод, с помощью которого передаем изображения для фона
    public void setBackground(BufferedImage value) {
        if (value != img) { // Если переданное изображение - это что то новое, то
            this.img = value;
            repaint(); // перерисовываем панель
        }
    }

    // Если панель больше, чем фото на ней - масштабируем фото
    @Override
    public void invalidate() {
        super.invalidate();
        // Если размеры изображения отличаются от окна, то:
        if (getWidth() > img.getWidth() || getHeight() > img.getHeight()
                || getWidth() < img.getWidth() || getHeight() < img.getHeight()) {
            scaled = getScaledInstanceToFill(img, getSize()); // рассчитываем масштабирущий коэффициент
        } else {
            scaled = img; // если не отличаются, то масштабирующий коэфф = 1
        }
    }

    // Основной метод перерисовки фона
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Если изображения загружено, то всегда выполняется
        if (scaled != null) {
            // получаем размеры панели с учетом масштабирубщего коэффициента
            int x = (getWidth() - scaled.getWidth()) / 2;
            int y = (getHeight() - scaled.getHeight()) / 2;
            g.drawImage(scaled, x, y, this); // рисуем изображение
            // Ниже добавлено мной - в теории для правильного отображения прозрачности фона
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
            super.paintComponent(g);
        }
    }

    // Рассчет масштабирующего коэффициент
    public static BufferedImage getScaledInstanceToFill(BufferedImage img, Dimension size) {
        double scaleFactor = getScaleFactorToFill(img, size); // получаем рассчитанный масштабирующий коэффициент
        return getScaledInstance(img, scaleFactor);
    }

    public static double getScaleFactorToFill(BufferedImage img, Dimension size) {
        double dScale = 1; // масштабирующий коэффициент
        if (img != null) { // если изображение есть, то получаем размеры изображения
            int imageWidth = img.getWidth();
            int imageHeight = img.getHeight();

            double dScaleWidth = getScaleFactor(imageWidth, size.width); // расчитываем масштабирущий коэффициент по ширине
            double dScaleHeight = getScaleFactor(imageHeight, size.height); // расчитываем масштабирущий коэффициент по высоте

            dScale = Math.max(dScaleHeight, dScaleWidth); // выбираем наибольший масштабирующий коэффициент
        }
        return dScale;
    }

    // iMasterSize = Размер (ширина/высота) изображения
    // iTargetSize = Размер (ширина/высота) панели
    public static double getScaleFactor(int iMasterSize, int iTargetSize) {
        double dScale = (double) iTargetSize / (double) iMasterSize; // расчитываем масштабирущий коэффициент по одному размеру (высоте или ширине)
        return dScale;
    }

    public static BufferedImage getScaledInstance(BufferedImage img, double dScaleFactor) {
        return getScaledInstance(img, dScaleFactor, RenderingHints.VALUE_INTERPOLATION_BILINEAR, true);
    }

    // Метод расчета размеров изображения, которые должны получится
    protected static BufferedImage getScaledInstance(BufferedImage img, double dScaleFactor, Object hint, boolean bHighQuality) {
        BufferedImage imgScale = img;

        // Расчет новых размеров изображения с учетом масштабирующего коэффициента
        int iImageWidth = (int) Math.round(img.getWidth() * dScaleFactor);
        int iImageHeight = (int) Math.round(img.getHeight() * dScaleFactor);

        if (dScaleFactor <= 1.0d) { // если изображение нужно уменьшить, то
            imgScale = getScaledDownInstance(img, iImageWidth, iImageHeight, hint, bHighQuality);
        } else { // если изображение нужно увеличить, то
            imgScale = getScaledUpInstance(img, iImageWidth, iImageHeight, hint, bHighQuality);
        }
        return imgScale; // возвращается изображения с измененными размерами
    }

}
