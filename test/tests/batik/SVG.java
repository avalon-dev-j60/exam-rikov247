package tests.batik;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.apache.batik.swing.JSVGCanvas;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SVG {    
    public static void main(final String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @SuppressWarnings("unused")
            @Override
            public void run() {
                new SVG();
            }
        });
    }

    public SVG() {
        final JFrame frame = new JFrame();
        final JPanel panel = new JPanel(new BorderLayout());
        final JSVGCanvas svgCanvas = new JSVGCanvas();

        frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel, java.awt.BorderLayout.CENTER);
        svgCanvas.setDocumentState(svgCanvas.ALWAYS_DYNAMIC);
        svgCanvas.setDocument(buildDocument());

        panel.add(svgCanvas);
        frame.pack();
        frame.setVisible(true);
    }

    private Document buildDocument() {
        final DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
        final String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
        final Document doc = impl.createDocument(svgNS, "svg", null);

        // get the root element (the svg element)
        final Element svgRoot = doc.getDocumentElement();

        // create the path element
        final Element element = doc.createElementNS(svgNS, "path");
        element.setAttribute("d", "M50,50 L30,50 A20,20 0 0,1 50,30 z");
        element.setAttribute("style", "fill:black;");

        svgRoot.appendChild(element);

        return doc;
    }
}
