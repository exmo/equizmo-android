package br.gov.serpro.quiz.service.soap;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import br.gov.serpro.quiz.exception.InvalidLoginException;
import br.gov.serpro.quiz.infrastructure.ksoap.DoubleMarshal;
import br.gov.serpro.quiz.model.User;
import br.gov.serpro.quiz.service.UserService;

/**
 * Implementação SOAP para o serviço de login.
 * 
 * @author Marlon Silva Carvalho
 * @since 1.0.0
 */
public class UserServiceSoap implements UserService {
	private static final String NAMESPACE = "http://webservice/";
	private static final String LOGIN_METHOD_NAME = "login";
	private static final String POINTS_METHOD_NAME = "addPoints";
	private static final String RANKING_METHOD_NAME = "ranking";

	@Override
	public Integer register(final String name, final String email, final Double latitude, final Double longitude) {
		Integer result = 0;
		final SoapObject request = new SoapObject(NAMESPACE, LOGIN_METHOD_NAME);
		final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

		DoubleMarshal doubleMarshal = new DoubleMarshal();
		doubleMarshal.register(envelope);

		request.addProperty("email", email);
		request.addProperty("name", name);
		request.addProperty("latitude", latitude);
		request.addProperty("longitude", longitude);
		envelope.setOutputSoapObject(request);

		try {
			final HttpTransportSE androidHttpTransport = new HttpTransportSE("http://quiz-exmo.rhcloud.com/user?wsdl");
			androidHttpTransport.call("", envelope);
			SoapPrimitive ret = (SoapPrimitive) envelope.getResponse();
			result = Integer.valueOf(ret.toString());
		} catch (Exception e) {
			throw new InvalidLoginException();
		}

		return result;
	}

	@Override
	public Integer sendScore(String email, Integer score) {
		Integer result = 0;
		final SoapObject request = new SoapObject(NAMESPACE, POINTS_METHOD_NAME);
		final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

		request.addProperty("email", email);
		request.addProperty("points", score);
		envelope.setOutputSoapObject(request);

		try {
			final HttpTransportSE androidHttpTransport = new HttpTransportSE("http://quiz-exmo.rhcloud.com/user?wsdl");
			androidHttpTransport.call("", envelope);
			final SoapPrimitive ret = (SoapPrimitive) envelope.getResponse();
			result = Integer.valueOf(ret.toString());
		} catch (Exception e) {
			throw new InvalidLoginException();
		}

		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> getRanking(final int quantity) {
		final List<User> result = new ArrayList<User>();
		final SoapObject request = new SoapObject(NAMESPACE, RANKING_METHOD_NAME);
		final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

		request.addProperty("offset", quantity);
		envelope.setOutputSoapObject(request);

		try {
			final HttpTransportSE androidHttpTransport = new HttpTransportSE("http://quiz-exmo.rhcloud.com/user?wsdl");
			androidHttpTransport.call("", envelope);
			Vector<SoapObject> vector = (Vector<SoapObject>) envelope.getResponse();
			for (SoapObject object : vector) {
				final User usuario = new User();
				usuario.email = object.getPropertyAsString("email");
				usuario.name = object.getPropertyAsString("name");
				usuario.latitude = Double.valueOf(object.getPropertyAsString("latitude"));
				usuario.longitude = Double.valueOf(object.getPropertyAsString("longitude"));
				usuario.score = Integer.valueOf(object.getPropertyAsString("points"));
				result.add(usuario);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

}
