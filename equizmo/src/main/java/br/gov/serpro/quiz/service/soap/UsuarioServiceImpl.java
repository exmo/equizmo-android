package br.gov.serpro.quiz.service.soap;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import br.gov.serpro.quiz.exception.LoginInvalidoException;
import br.gov.serpro.quiz.infrastructure.ksoap.DoubleMarshal;
import br.gov.serpro.quiz.model.Usuario;
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
	private static final String RANKING_METHOD_NAME = "ranking";

	@Override
	public Integer registrar(final String nome, final String email, final Double latitude, final Double longitude) {
		Integer result = 0;
		final SoapObject request = new SoapObject(NAMESPACE, LOGIN_METHOD_NAME);
		final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

		DoubleMarshal doubleMarshal = new DoubleMarshal();
		doubleMarshal.register(envelope);

		request.addProperty("email", email);
		request.addProperty("name", nome);
		request.addProperty("latitude", latitude);
		request.addProperty("longitude", longitude);
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

	@Override
	@SuppressWarnings("unchecked")
	public List<Usuario> obterRanking() {
		final List<Usuario> result = new ArrayList<Usuario>();
		final SoapObject request = new SoapObject(NAMESPACE, RANKING_METHOD_NAME);
		final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

		request.addProperty("offset", 5);
		envelope.setOutputSoapObject(request);

		try {
			final HttpTransportSE androidHttpTransport = new HttpTransportSE("http://quiz-exmo.rhcloud.com/user?wsdl");
			androidHttpTransport.call("", envelope);
			Vector<SoapObject> vector = (Vector<SoapObject>) envelope.getResponse();
			for (SoapObject object : vector) {
				final Usuario usuario = new Usuario();
				usuario.email = object.getPropertyAsString("email");
				usuario.nome = object.getPropertyAsString("name");
				usuario.latitude = Double.valueOf(object.getPropertyAsString("latitude"));
				usuario.longitude = Double.valueOf(object.getPropertyAsString("longitude"));
				usuario.pontuacao = Integer.valueOf(object.getPropertyAsString("points"));
				result.add(usuario);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

}
