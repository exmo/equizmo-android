package br.gov.serpro.quiz.service.soap;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import br.gov.serpro.quiz.model.Categoria;
import br.gov.serpro.quiz.service.CategoriaService;

public class CategoriaServiceImpl implements CategoriaService {

	private static final String NAMESPACE = "http://webservice/";
	private static final String METHOD_NAME = "listCategories";

	@SuppressWarnings("unchecked")
	@Override
	public List<Categoria> obterCategorias() {
		final List<Categoria> result = new ArrayList<Categoria>();
		final SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);

		try {
			final HttpTransportSE androidHttpTransport = new HttpTransportSE("http://quiz-exmo.rhcloud.com/quiz?wsdl");
			androidHttpTransport.call("", envelope);
			final Vector<SoapPrimitive> vector = (Vector<SoapPrimitive>) envelope.getResponse();
			for (SoapPrimitive nome : vector) {
				result.add(new Categoria(nome.toString()));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return result;
	}

}
