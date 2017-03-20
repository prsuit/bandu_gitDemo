package com.tt.bean;

import java.util.List;

/**
 * 创建者：Mckiera
 * <p>时间：2016-07-21  16:53
 * <p>类描述：
 * <p>修改人：
 * <p>修改时间：
 * <p>修改备注：
 */
public class SkResult {

    /**
     * applicationId : 1255202120000c
     * dtLastResponse : 2016-07-21 15:06:02:369
     * eof : 1
     * params : {"app":{"applicationId":"1255202120000c","clientId":"a399100e534ed27","sig":"17ed9f80312f6f3be6dacb542ca814138a4a8357","timestamp":"1469084759","userId":"test.tt.com"},"audio":{"audioType":"ogg","channel":1,"compress":"speex","sampleBytes":2,"sampleRate":16000},"request":{"coreType":"en.sent.score","rank":100,"refText":"This is Mary's book.","tokenId":"579074573327934f88000002","type":"readaloud"}}
     * recordId : 579074577e49f76033000004
     * refText : This is Mary's book.
     * result : {"fluency":100,"oov":"book.","overall":6,"pronunciation":7,"words":[{"scores":{"overall":9,"pronunciation":9},"word":"this"},{"scores":{"overall":7,"pronunciation":7},"word":"is"},{"scores":{"overall":7,"pronunciation":7},"word":"mary's"}]}
     * tokenId : 579074573327934f88000002
     */

    private String applicationId;
    private String dtLastResponse;
    private String audioUrl;
    private int eof;

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    /**
     * app : {"applicationId":"1255202120000c","clientId":"a399100e534ed27","sig":"17ed9f80312f6f3be6dacb542ca814138a4a8357","timestamp":"1469084759","userId":"test.tt.com"}
     * audio : {"audioType":"ogg","channel":1,"compress":"speex","sampleBytes":2,"sampleRate":16000}
     * request : {"coreType":"en.sent.score","rank":100,"refText":"This is Mary's book.","tokenId":"579074573327934f88000002","type":"readaloud"}
     */

    private ParamsBean params;
    private String recordId;
    private String refText;
    /**
     * fluency : 100
     * oov : book.
     * overall : 6
     * pronunciation : 7
     * words : [{"scores":{"overall":9,"pronunciation":9},"word":"this"},{"scores":{"overall":7,"pronunciation":7},"word":"is"},{"scores":{"overall":7,"pronunciation":7},"word":"mary's"}]
     */

    private ResultBean result;
    private String tokenId;

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getDtLastResponse() {
        return dtLastResponse;
    }

    public void setDtLastResponse(String dtLastResponse) {
        this.dtLastResponse = dtLastResponse;
    }

    public int getEof() {
        return eof;
    }

    public void setEof(int eof) {
        this.eof = eof;
    }

    public ParamsBean getParams() {
        return params;
    }

    public void setParams(ParamsBean params) {
        this.params = params;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getRefText() {
        return refText;
    }

    public void setRefText(String refText) {
        this.refText = refText;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public static class ParamsBean {
        /**
         * applicationId : 1255202120000c
         * clientId : a399100e534ed27
         * sig : 17ed9f80312f6f3be6dacb542ca814138a4a8357
         * timestamp : 1469084759
         * userId : test.tt.com
         */

        private AppBean app;
        /**
         * audioType : ogg
         * channel : 1
         * compress : speex
         * sampleBytes : 2
         * sampleRate : 16000
         */

        private AudioBean audio;
        /**
         * coreType : en.sent.score
         * rank : 100
         * refText : This is Mary's book.
         * tokenId : 579074573327934f88000002
         * type : readaloud
         */

        private RequestBean request;

        public AppBean getApp() {
            return app;
        }

        public void setApp(AppBean app) {
            this.app = app;
        }

        public AudioBean getAudio() {
            return audio;
        }

        public void setAudio(AudioBean audio) {
            this.audio = audio;
        }

        public RequestBean getRequest() {
            return request;
        }

        public void setRequest(RequestBean request) {
            this.request = request;
        }

        public static class AppBean {
            private String applicationId;
            private String clientId;
            private String sig;
            private String timestamp;
            private String userId;

            public String getApplicationId() {
                return applicationId;
            }

            public void setApplicationId(String applicationId) {
                this.applicationId = applicationId;
            }

            public String getClientId() {
                return clientId;
            }

            public void setClientId(String clientId) {
                this.clientId = clientId;
            }

            public String getSig() {
                return sig;
            }

            public void setSig(String sig) {
                this.sig = sig;
            }

            public String getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(String timestamp) {
                this.timestamp = timestamp;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }
        }

        public static class AudioBean {
            private String audioType;
            private int channel;
            private String compress;
            private int sampleBytes;
            private int sampleRate;

            public String getAudioType() {
                return audioType;
            }

            public void setAudioType(String audioType) {
                this.audioType = audioType;
            }

            public int getChannel() {
                return channel;
            }

            public void setChannel(int channel) {
                this.channel = channel;
            }

            public String getCompress() {
                return compress;
            }

            public void setCompress(String compress) {
                this.compress = compress;
            }

            public int getSampleBytes() {
                return sampleBytes;
            }

            public void setSampleBytes(int sampleBytes) {
                this.sampleBytes = sampleBytes;
            }

            public int getSampleRate() {
                return sampleRate;
            }

            public void setSampleRate(int sampleRate) {
                this.sampleRate = sampleRate;
            }
        }

        public static class RequestBean {
            private String coreType;
            private int rank;
            private String refText;
            private String tokenId;
            private String type;

            public String getCoreType() {
                return coreType;
            }

            public void setCoreType(String coreType) {
                this.coreType = coreType;
            }

            public int getRank() {
                return rank;
            }

            public void setRank(int rank) {
                this.rank = rank;
            }

            public String getRefText() {
                return refText;
            }

            public void setRefText(String refText) {
                this.refText = refText;
            }

            public String getTokenId() {
                return tokenId;
            }

            public void setTokenId(String tokenId) {
                this.tokenId = tokenId;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }

    public static class ResultBean {
        private int fluency;
        private String oov;
        private int overall;
        private int pronunciation;
        /**
         * scores : {"overall":9,"pronunciation":9}
         * word : this
         */

        private List<WordsBean> words;

        public int getFluency() {
            return fluency;
        }

        public void setFluency(int fluency) {
            this.fluency = fluency;
        }

        public String getOov() {
            return oov;
        }

        public void setOov(String oov) {
            this.oov = oov;
        }

        public int getOverall() {
            return overall;
        }

        public void setOverall(int overall) {
            this.overall = overall;
        }

        public int getPronunciation() {
            return pronunciation;
        }

        public void setPronunciation(int pronunciation) {
            this.pronunciation = pronunciation;
        }

        public List<WordsBean> getWords() {
            return words;
        }

        public void setWords(List<WordsBean> words) {
            this.words = words;
        }

        public static class WordsBean {
            /**
             * overall : 9
             * pronunciation : 9
             */

            private ScoresBean scores;
            private String word;

            public ScoresBean getScores() {
                return scores;
            }

            public void setScores(ScoresBean scores) {
                this.scores = scores;
            }

            public String getWord() {
                return word;
            }

            public void setWord(String word) {
                this.word = word;
            }

            public static class ScoresBean {
                private int overall;
                private int pronunciation;

                public int getOverall() {
                    return overall;
                }

                public void setOverall(int overall) {
                    this.overall = overall;
                }

                public int getPronunciation() {
                    return pronunciation;
                }

                public void setPronunciation(int pronunciation) {
                    this.pronunciation = pronunciation;
                }
            }
        }
    }
}
