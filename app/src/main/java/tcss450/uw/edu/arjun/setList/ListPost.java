package tcss450.uw.edu.arjun.setList;

import java.io.Serializable;

/**
 * Class to encapsulate a Phish.net Blog Post. Building an Object requires a publish date and title.
 *
 * Optional fields include URL, teaser, and Author.
 *
 *
 * @author Charles Bryan
 * @version 14 September 2018
 */
public class ListPost implements Serializable {

    private final String mLongDate;
    private final String mLocation;
    private final String mVenue;
    private final String mSetListData;
    private final String mlistNotes;
    private final String mUrl;


    /**
     * Helper class for building Credentials.
     *
     * @author Arjun Prajapati
     */
    public static class Builder {
        private final String mLongDate;
        private final String mLocation;
        private final String mVenue;
        private  String mSetListData;
        private  String mlistNotes;
        private   String mUrl = "";



        /**
         * Constructs a new Builder.
         *
         * @param venue, the venue
         */
        public Builder(String date, String location, String venue) {
            this.mLongDate = date;
            this.mLocation = location;
            this.mVenue = venue;
        }

        /**
         * Add an optional url for the full blog post.
         * @param val an optional url for the full blog post
         * @return the Builder of this BlogPost
         */
        public Builder addUrl(final String val) {
            mUrl = val;
            return this;
        }

        /**
         * Add an optional teaser for the full blog post.
         * @param val an optional url teaser for the full blog post.
         * @return the Builder of this BlogPost
         */
        public Builder addsetListData(final String val) {
            mSetListData = val;
            return this;
        }

        /**
         * Add an optional author of the blog post.
         * @param val an optional author of the blog post.
         * @return the Builder of this BlogPost
         */
        public Builder addlistnote(final String val) {
            mlistNotes = val;
            return this;
        }

        public ListPost build() {
            return new ListPost(this);
        }

    }

    private ListPost(final Builder builder) {
        this.mlistNotes = builder.mlistNotes;
        this.mLocation = builder.mLocation;
        this.mLongDate = builder.mLongDate;
        this.mSetListData = builder.mSetListData;
        this.mVenue = builder.mVenue;
        this.mUrl = builder.mUrl;



    }

    public String getmlistNotes() {
        return mlistNotes;
    }

    public String getmLocation() {
        return mLocation;
    }

    public String getmUrl() {
        return mUrl;
    }

    public String getmLongDate() {
        return mLongDate;
    }

    public String getmSetListData() {
        return mSetListData;
    }
    public String getmVenue() {
        return mVenue;
    }


}
