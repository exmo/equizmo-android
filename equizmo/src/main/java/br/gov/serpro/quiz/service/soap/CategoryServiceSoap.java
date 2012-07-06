package br.gov.serpro.quiz.service.soap;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import br.gov.serpro.quiz.model.Category;
import br.gov.serpro.quiz.service.CategoryService;

public class CategoryServiceSoap implements CategoryService {

	private static final String NAMESPACE = "http://webservice/";
	private static final String METHOD_NAME = "listCategories";

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getCategories() {
		final List<Category> result = new ArrayList<Category>();
		final SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);

		try {
			final HttpTransportSE androidHttpTransport = new HttpTransportSE("http://quiz-exmo.rhcloud.com/quiz?wsdl");
			androidHttpTransport.call("", envelope);
			final Vector<SoapPrimitive> vector = (Vector<SoapPrimitive>) envelope.getResponse();
			for (SoapPrimitive nome : vector) {
				result.add(new Category(nome.toString()));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return result;
	}

}
