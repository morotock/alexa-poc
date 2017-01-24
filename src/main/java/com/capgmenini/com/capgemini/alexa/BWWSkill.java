package com.capgmenini.com.capgemini.alexa;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.*;
import com.amazon.speech.speechlet.servlet.SpeechletServlet;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import org.apache.log4j.Logger;

/**
 * Created by ekeeton on 1/17/17.
 * <h2>Examples</h2>
 * <p>
 * <b>Dialog model</b>
 * <p>
 * Customer: "Alexa, order BWWs."
 * <p>
 * Alexa: "Your favorite location?"
 * <p>
 * Customer: "Yes"
 * <p>
 * Alexa: "What would you like to order, wings or burger?"
 * <p>
 * Customer: "Wings"
 * <p>
 * Alexa: "How Many?"
 * <p>
 * Customer: 12
 * <p>
 * Alexa: "What sauce?"
 * <p>
 * Customer: "Salt and Vinegar"
 * <p>
 * Alexa: "Anything else?"
 * <p>
 * Customer: "Nope"
 * <p>
 * Alexa: "Order placed , costs $15.23 and ready in 15 minutes "
 */
public class BWWSkill extends SpeechletServlet implements Speechlet {
    // TODO:  Refactor repeated code in handler methods
    private static final Logger Log = Logger.getLogger(BWWSkill.class);
    /**
     * Session attribute to store the stage the order is at.
     */
    private static final String SESSION_STAGE = "stage";
    /**
     *
     */
    private static final int LOCATION_STAGE = 1;
    private static final int ORDER_STAGE = 2;
    private static final int HOW_MANY_STAGE = 3;
    private static final int SAUCE_STAGE = 4;
    private static final int ANYTHING_ELSE_STAGE = 5;

    public BWWSkill() {
        setSpeechlet(this);
    }

    public void onSessionStarted(SessionStartedRequest sessionStartedRequest, Session session) throws SpeechletException {
        Log.info(String.format("Session Started for requestId=%s sessionId=%s userId=%s",
                sessionStartedRequest.getRequestId(), session.getSessionId(),
                session.getUser().getUserId()));
    }

    public SpeechletResponse onLaunch(LaunchRequest launchRequest, Session session) throws SpeechletException {
        Log.info(String.format("Session Started for requestId=%s sessionId=%s userId=%s",
                launchRequest.getRequestId(), session.getSessionId(),
                session.getUser().getUserId()));
        return handleTellLocationIntent(session);
    }

    private SpeechletResponse handleTellLocationIntent(final Session session) {
        String speechOutput = "Your favorite Buffalo Wild Wings location?";
        String repromptText = "You may order from your favorite Buffalo Wild Wings location.  Would you like to order" +
                " from your favorite Buffalo Wild Wings location? Your response should be 'Yes'";

        final SpeechletResponse response = new SpeechletResponse();
        final PlainTextOutputSpeech output = new PlainTextOutputSpeech();
        final PlainTextOutputSpeech repromptSpeech = new PlainTextOutputSpeech();
        final Reprompt reprompt = new Reprompt();
        output.setText(speechOutput);
        response.setOutputSpeech(output);
        repromptSpeech.setText(repromptText);
        reprompt.setOutputSpeech(repromptSpeech);
        response.setReprompt(reprompt);
        response.setShouldEndSession(false);

        session.setAttribute(SESSION_STAGE, LOCATION_STAGE);

        return response;
    }

    public SpeechletResponse onIntent(IntentRequest request, Session session) throws SpeechletException {
        Log.info(String.format("Session Started for requestId=%s sessionId=%s userId=%s",
                request.getRequestId(), session.getSessionId(),
                session.getUser().getUserId()));

        final SpeechletResponse response = new SpeechletResponse();
        final PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText("I don't understand what you are talking about.");

        Intent intent = request.getIntent();
        String intentName = intent != null ? intent.getName() : null;
        if (intentName.equals("TellLocation")) {
            return handleTellLocationIntent(session);
        } else if (intentName.equals("TellItemToOrder")) {
            return handleTellItemToOrderIntent(session);
        } else if (intentName.equals("TellHowMany")) {
            return handleTellHowManyIntent(session);
        } else if (intentName.equals("TellSauce")) {
            return handleTellSauceIntent(session);
        } else if (intentName.equals("TellAnythingElse")) {
            return handleTellAnythingElseIntent(session);
        } else {
            throw new SpeechletException("Invalid Intent");
        }

    }

    private SpeechletResponse handleTellSauceIntent(Session session) {
        String speech = "What sauce?";
        String repromptText = "Tell me what sauce you would like.?";

        final SpeechletResponse response = new SpeechletResponse();
        final PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
        final Reprompt reprompt = new Reprompt();
        final PlainTextOutputSpeech repromptSpeech = new PlainTextOutputSpeech();

        outputSpeech.setText(speech);
        response.setOutputSpeech(outputSpeech);
        repromptSpeech.setText(repromptText);
        reprompt.setOutputSpeech(repromptSpeech);
        response.setReprompt(reprompt);
        response.setShouldEndSession(false);

        session.setAttribute(SESSION_STAGE, SAUCE_STAGE);
        return response;
    }

    private SpeechletResponse handleTellAnythingElseIntent(Session session) {
        return null;
    }

    private SpeechletResponse handleTellHowManyIntent(Session session) {
        String speech = "How many?";
        String repromptText = "Tell me how many you would like to order?";

        final SpeechletResponse response = new SpeechletResponse();
        final PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
        final Reprompt reprompt = new Reprompt();
        final PlainTextOutputSpeech repromptSpeech = new PlainTextOutputSpeech();

        outputSpeech.setText(speech);
        response.setOutputSpeech(outputSpeech);
        repromptSpeech.setText(repromptText);
        reprompt.setOutputSpeech(repromptSpeech);
        response.setReprompt(reprompt);
        response.setShouldEndSession(false);
        response.setShouldEndSession(false);

        session.setAttribute(SESSION_STAGE, HOW_MANY_STAGE);
        return response;
    }

    private SpeechletResponse handleTellItemToOrderIntent(Session session) {
        String speechOutput = "What would you like to order, wings or burger?";
        String repromptText = "You may say wings, or burger";

        final SpeechletResponse response = new SpeechletResponse();
        final PlainTextOutputSpeech output = new PlainTextOutputSpeech();
        final Reprompt reprompt = new Reprompt();
        final PlainTextOutputSpeech repromptSpeech = new PlainTextOutputSpeech();

        output.setText(speechOutput);
        response.setOutputSpeech(output);
        repromptSpeech.setText(repromptText);
        reprompt.setOutputSpeech(repromptSpeech);
        response.setReprompt(reprompt);
        response.setShouldEndSession(false);

        session.setAttribute(SESSION_STAGE, ORDER_STAGE);

        return response;
    }

    public void onSessionEnded(SessionEndedRequest sessionEndedRequest, Session session) throws SpeechletException {
        Log.info("Session Ended for " + session.getUser().getUserId());
    }
}
