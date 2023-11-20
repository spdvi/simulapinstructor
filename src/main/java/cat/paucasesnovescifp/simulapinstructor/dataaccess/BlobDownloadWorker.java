package cat.paucasesnovescifp.simulapinstructor.dataaccess;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.ProgressReceiver;
import com.azure.storage.blob.implementation.models.BlobName;
import com.azure.storage.blob.models.BlobProperties;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class BlobDownloadWorker extends SwingWorker<Void, Integer> {

    private static final String connectionString = "DefaultEndpointsProtocol=https;AccountName=simulapfileserver;AccountKey=rARWVR8b+HYR9t3Clc7SSYSKg3ziOhmItZUUdNMqSbV70r8xHhXYDw17dtNF13Ftujtj7UOZBRH5+AStTP81ig==;EndpointSuffix=core.windows.net";
    ;
    private static final String containerName = "simulapvideoscontainer";
    private String blobName = "your_blob_name";
    private File downloadPath;

    private JProgressBar progressBar;
    private JLabel statusLabel;

    public BlobDownloadWorker(JProgressBar progressBar, JLabel statusLabel, String blobName, File downloadPath) {
        this.progressBar = progressBar;
        this.statusLabel = statusLabel;
        this.blobName = blobName;
        this.downloadPath = downloadPath;
    }

    @Override
    protected Void doInBackground() throws Exception {
        try {
            // Initialize BlobServiceClient
            BlobContainerClient blobContainerClient = new BlobServiceClientBuilder()
                    .connectionString(connectionString)
                    .buildClient()
                    .getBlobContainerClient(containerName);

            // Get BlobClient
            BlobClient blobClient = blobContainerClient.getBlobClient(blobName);

            // Get blob properties to determine content length
            BlobProperties properties = blobClient.getProperties();
            long blobSize = properties.getBlobSize();

            // Download the blob
            //Path localFilePath = Paths.get(downloadPath, blobClient.getBlobName());

            // Set the progress bar maximum value to the content length
//            progressBar.setMaximum((int) blobSize);

//            blobClient.downloadToFile(localFilePath.toString(), (receivedBytes, totalBytes) -> {
//                // Update the progress bar in the EDT
//                setProgress((int) receivedBytes);
//            });
            try (OutputStream outputStream = new ProgressOutputStream(new FileOutputStream(downloadPath), blobSize, (bytesWritten, size) -> {
                int percent = (int) (bytesWritten * 100 / size);
                setProgress(percent);
            })) {
//                blobClient.download(outputStream);
                    blobClient.downloadStream(outputStream);
            }

            // Update the status label
            statusLabel.setText("Download complete");

        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Download failed");
        }

        return null;
    }

//    @Override
//    protected void process(java.util.List<Integer> chunks) {
//        // Update the progress bar in the EDT
//        for (Integer chunk : chunks) {
//            progressBar.setValue(chunk);
//        }
//    }
//
//    @Override
//    protected void done() {
//        // Perform any final updates in the EDT
//        progressBar.setValue(progressBar.getMaximum());
//    }
}
