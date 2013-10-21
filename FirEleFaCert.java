
import es.mityc.facturae.utils.SigningException;
import es.mityc.javasign.bridge.ConfigurationException;
import java.io.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;

public class FirEleFa {
	public static void main(String[] args) throws SigningException, ConfigurationException, TransformerException
	{
	  String Dato2 = args[0];  //Entrada
	  String Dato3 = args[1]; //Salida
	  //String Dato4 = args[2]; //Explorer
	  String certificadoPath = args[2];
	  
	  //Certificado
	  java.io.InputStream inStream = new java.io.FileInputStream (certificadoPath);
	  java.security.cert.CertificateFactory cf =  java.security.cert.CertificateFactory.getInstance("X.509");
	  org.mozilla.jss.crypto.X509Certificate cert = (org.mozilla.jss.crypto.X509Certificate)cf.generateCertificate(inStream);
	  //////////////////////

	  java.io.File factura32 = new java.io.File( Dato2 );
	  javax.xml.parsers.DocumentBuilderFactory dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();
	  javax.xml.parsers.DocumentBuilder db = dbf.newDocumentBuilder();
	  org.w3c.dom.Document doc = db.parse(factura32);
			        

  	  //java.io.File factura32 = new java.io.File( Dato2 );
	  //Document Dato6 = es.mityc.facturae.utils.SignatureUtil.sign( factura32 , Dato4 , "" );
	  Document Dato6 = es.mityc.facturae.utils.SignatureUtil.sign(doc,cert);

	  TransformerFactory transformerFactory = TransformerFactory.newInstance();
	  Transformer transformer = transformerFactory.newTransformer();
	  DOMSource source = new DOMSource(Dato6);
	  StreamResult result = new StreamResult(new File( Dato3 ));
 	  transformer.transform(source, result);
	}
}