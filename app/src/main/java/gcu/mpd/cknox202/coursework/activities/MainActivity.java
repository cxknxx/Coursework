package gcu.mpd.cknox202.coursework.activities;
/*S1514428
Cameron Knox
Computing
*/
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import gcu.mpd.cknox202.coursework.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new DownloadTask(this).execute();
    }

    private static class DownloadTask extends AsyncTask<Void, Integer, Void> {

        int progressStatus;
        StringBuilder result;
        URL url;
        private WeakReference<MainActivity> activityReference;

        DownloadTask(MainActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressStatus = 0;
            result = new StringBuilder();

            try {
                url = new URL("http://quakes.bgs.ac.uk/feeds/MhSeismology.xml");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            URLConnection connection;
            BufferedReader in;

            try {
                connection = url.openConnection();
                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String line;

                // skip first 2 lines
                in.readLine();
                in.readLine();

                int totalLines = connection.getContentLength();

                while ((line = in.readLine()) != null) {
                    if (line.equalsIgnoreCase("</rss>")) break;

                    line = line.replace("geo:lat", "lat");
                    line = line.replace("geo:long", "lon");

                    result.append(line);

                    progressStatus++;
                    double d = ((double) progressStatus / (double) totalLines) * 100;
                    publishProgress((int)d);
                }

                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            ProgressBar downloadProgress = activityReference.get().findViewById(R.id.downloadProgress);

            downloadProgress.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (!this.result.toString().equals("")) {
                Intent intent = new Intent(activityReference.get(), HomeActivity.class);
                intent.putExtra("data", this.result.toString());
                activityReference.get().startActivity(intent);
                activityReference.get().finish();
            }
        }
    }

}
