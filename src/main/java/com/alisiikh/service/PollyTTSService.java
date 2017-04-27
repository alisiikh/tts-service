package com.alisiikh.service;

import com.alisiikh.model.TTSRequest;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.polly.AmazonPollyClient;
import com.amazonaws.services.polly.model.*;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.ws.rs.BadRequestException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author alisiikh
 */
@ApplicationScoped
@Named("pollyTTSService")
public class PollyTTSService implements TTSService {

    private AmazonPollyClient polly;

    @PostConstruct
    protected void initPollyClient() {
        polly = new AmazonPollyClient(new EnvironmentVariableCredentialsProvider(),
                new ClientConfiguration());
        polly.setRegion(Region.getRegion(Regions.EU_WEST_1));
    }

    @Override
    public InputStream transformTextToSpeech(TTSRequest ttsRequest) {
        try {
            return synthesize(ttsRequest.getText(), ttsRequest.getVoice(), OutputFormat.Mp3);
        } catch (IOException e) {
            throw new BadRequestException("Failed to process request to AWS Polly.", e);
        }
    }

    @Override
    public List<String> getAvailableVoices() {
        DescribeVoicesRequest describeVoicesRequest = new DescribeVoicesRequest();
        DescribeVoicesResult describeVoicesResult = polly.describeVoices(describeVoicesRequest);

        return describeVoicesResult.getVoices().parallelStream()
                .map(Voice::getId)
                .collect(Collectors.toList());
    }

    private Voice getVoiceById(String voiceId) {
        DescribeVoicesResult describeVoicesResult = polly.describeVoices(new DescribeVoicesRequest());
        return describeVoicesResult.getVoices().parallelStream()
                .findAny()
                .filter(voice -> voice.getId().equals(voiceId))
                .orElseGet(null);
    }

    private InputStream synthesize(String text, String voiceId, OutputFormat format) throws IOException {
        SynthesizeSpeechRequest synthReq = new SynthesizeSpeechRequest().withText(text).withVoiceId(voiceId)
                        .withOutputFormat(format);
        SynthesizeSpeechResult synthRes = polly.synthesizeSpeech(synthReq);

        return synthRes.getAudioStream();
    }
}
