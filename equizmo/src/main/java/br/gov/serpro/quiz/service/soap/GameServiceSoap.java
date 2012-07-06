package br.gov.serpro.quiz.service.soap;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import br.gov.serpro.quiz.model.Category;
import br.gov.serpro.quiz.model.Proposition;
import br.gov.serpro.quiz.model.Question;
import br.gov.serpro.quiz.service.GameService;

public class GameServiceSoap implements GameService {

	private static final String NAMESPACE = "http://webservice/";
	private static final String METHOD_NAME = "giveMeAQuiz";

	@Override
	public List<Question> getQuestions(final Category category) {
		final List<Question> result = new ArrayList<Question>();
		final SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		request.addProperty("category", category.name);
		envelope.setOutputSoapObject(request);

		try {
			final HttpTransportSE androidHttpTransport = new HttpTransportSE("http://quiz-exmo.rhcloud.com/quiz?wsdl");
			androidHttpTransport.call("", envelope);
			SoapObject object = (SoapObject) envelope.getResponse();
			for (int indice = 0; indice < object.getPropertyCount(); indice++) {
				Object property = object.getProperty(indice);
				if (property instanceof SoapObject) {
					SoapObject questaoSoap = (SoapObject) property;

					final Question questao = new Question();
					questao.enunciation = questaoSoap.getPropertyAsString("enunciation");
					questao.indexCorrectProposition = Integer.valueOf(questaoSoap.getPropertyAsString("answer"));

					for (int indice2 = 0; indice2 < questaoSoap.getPropertyCount(); indice2++) {
						PropertyInfo pi = new PropertyInfo();
						questaoSoap.getPropertyInfo(indice2, pi);
						if ("propositions".equals(pi.getName())) {
							questao.propositions.add(new Proposition(pi.getValue().toString()));
						}
					}

					result.add(questao);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return result;
	}
}
