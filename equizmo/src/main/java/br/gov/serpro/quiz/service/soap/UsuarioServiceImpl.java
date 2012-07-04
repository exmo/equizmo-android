package br.gov.serpro.quiz.service.soap;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import br.gov.serpro.quiz.exception.LoginInvalidoException;
import br.gov.serpro.quiz.service.UsuarioService;

/**
 * Implementação SOAP para o serviço de login.
 * 
 * @author Marlon Silva Carvalho
 * @since 1.0.0
 */
public class UsuarioServiceImpl implements UsuarioService {
	private static final String NAMESPACE = "http://webservice/";
	private static final String LOGIN_METHOD_NAME = "login";
	private static final String POINTS_METHOD_NAME = "addPoints";

	@Override
	public Integer registrar(final String nome, final String email) {
		Integer result = 0;
		final SoapObject request = new SoapObject(NAMESPACE, LOGIN_METHOD_NAME);
		final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

		request.addProperty("email", email);
		request.addProperty("name", nome);
		request.addProperty("latitude", 1);
		request.addProperty("longitude", 1);
		envelope.setOutputSoapObject(request);

		try {
			final HttpTransportSE androidHttpTransport = new HttpTransportSE("http://quiz-exmo.rhcloud.com/user?wsdl");
			androidHttpTransport.call("", envelope);
			SoapPrimitive ret = (SoapPrimitive) envelope.getResponse();
			result = Integer.valueOf(ret.toString());
		} catch (Exception e) {
			throw new LoginInvalidoException();
		}

		return result;
	}

	@Override
	public Integer enviarPontos(String email, Integer pontos) {
		Integer result = 0;
		final SoapObject request = new SoapObject(NAMESPACE, POINTS_METHOD_NAME);
		final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

		request.addProperty("email", email);
		request.addProperty("points", pontos);
		envelope.setOutputSoapObject(request);

		try {
			final HttpTransportSE androidHttpTransport = new HttpTransportSE("http://quiz-exmo.rhcloud.com/user?wsdl");
			androidHttpTransport.call("", envelope);
			final SoapPrimitive ret = (SoapPrimitive) envelope.getResponse();
			result = Integer.valueOf(ret.toString());
		} catch (Exception e) {
			throw new LoginInvalidoException();
		}

		return result;
	}

}
