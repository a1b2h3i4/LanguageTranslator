import java.awt.*;
import java.io.*;
import java.awt.ActiveEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
class DevnagariTest {

    private static Font createFont(String url) throws FontFormatException, IOException{
        return Font.createFont(
                    Font.TRUETYPE_FONT,
                    DevnagariTest.class.getClassLoader().getResourceAsStream(url))
                .deriveFont(30f);
    }

    private static JLabel getLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }

    public static void main(String[] args) throws FontFormatException, IOException {
        Font mangal = createFont("mangal.ttf");
        Font arial = createFont("ARIALUNI.TTF");

        JFrame frame = new JFrame();

        final Box box = Box.createVerticalBox();
        box.add(getLabel("गणेश वार्ड रमेश सुरेश पप्पू पृथ्वी", mangal));
        box.add(getLabel("गणेश वार्ड रमेश सुरेश पप्पू पृथ्वी", arial));

        JButton print = new JButton("Print");
      
        print.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                PrinterJob job = PrinterJob.getPrinterJob();
                job.setPrintable(new Printable()
                {
        @Override
                    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
                            throws PrinterException {
                        if (pageIndex > 0) {
                            return NO_SUCH_PAGE;
                        }

                        Graphics2D g2 = (Graphics2D) graphics;
                        g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
                        box.printAll(g2);
                        g2.dispose();

                        return PAGE_EXISTS;
                    }

                });
                try {
                    job.print();
                } catch (PrinterException ex) {
                    ex.printStackTrace();
                }
            }

        });

        frame.add(box, BorderLayout.CENTER);
        frame.add(print, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}