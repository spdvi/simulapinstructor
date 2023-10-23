package cat.paucasesnovescifp.simulapinstructor;

import cat.paucasesnovescifp.simulapinstructor.dataaccess.DataAccess;
import cat.paucasesnovescifp.simulapinstructor.models.Intent;
import cat.paucasesnovescifp.simulapinstructor.models.Review;
import cat.paucasesnovescifp.simulapinstructor.models.Usuari;
import cat.paucasesnovescifp.simulapinstructor.views.LoginPanel;
import cat.paucasesnovescifp.simulapinstructor.views.RegisterDialog;
import cat.paucasesnovescifp.simulapinstructor.views.UserInfoPanel;
import com.azure.core.util.Context;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobRange;
import com.azure.storage.blob.models.DownloadRetryOptions;
import com.azure.storage.blob.specialized.BlockBlobClient;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

/**
 *
 * @author Miguel
 */
public class Main extends javax.swing.JFrame implements Runnable {

    private Usuari usuari = null;
    private LoginPanel pnlLogin = null;
    private UserInfoPanel pnlUserInfo = null;
    private DataAccess dataAccess = null;
    private JList<Intent> lstIntents = new JList<Intent>();
    private final EmbeddedMediaPlayerComponent mediaPlayerComponent;
    private JFileChooser fileChooser;
    private boolean isPlaying = false;
    // Azure blob storage for videos
    private final static String connectionString
            = "DefaultEndpointsProtocol=https;AccountName=simulapfileserver;AccountKey=rARWVR8b+HYR9t3Clc7SSYSKg3ziOhmItZUUdNMqSbV70r8xHhXYDw17dtNF13Ftujtj7UOZBRH5+AStTP81ig==;EndpointSuffix=core.windows.net";
    private BlobServiceClient blobServiceClient;
    private BlobContainerClient containerClient;
    //Create a unique name for the container
    private final static String containerName = "simulapvideoscontainer";
    private Thread downloadThread;

    /**
     * Creates new form Main
     */
    public Main() {
        initComponents();

        //System.out.println(System.getProperty("java.io.tmpdir"));
        setTitle("Simulap instructor U+1F4AA");
        setSize(1024, 768);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        dataAccess = new DataAccess();

        if (usuari == null) {
            pnlLogin = new LoginPanel(this);
            pnlLoginContainer.add(pnlLogin);
        } else {
            pnlLoginContainer.removeAll();
        }

        jScrollPane1.setViewportView(lstIntents);
        lstIntents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstIntents.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent evt) {
                lstIntentsValueChanged(evt);
            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                mediaPlayerComponent.release();
                System.exit(0);
            }
        });

        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        mediaPlayerComponent.mediaPlayer().events().addMediaPlayerEventListener(
                new MediaPlayerEventAdapter() {
            @Override
            public void playing(MediaPlayer mediaPlayer) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        btnPause.setText("Pause");
                        isPlaying = true;
//                                System.out.println("IsPlaying true");
                    }
                });
            }

            @Override
            public void finished(MediaPlayer mediaPlayer) {
            }

            @Override
            public void error(MediaPlayer mediaPlayer) {
            }

            @Override
            public void paused(MediaPlayer mediaPlayer) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        btnPause.setText("Resume");
                        isPlaying = false;
//                                System.out.println("IsPaused true");
                    }
                });
            }

        }
        );
        pnlVideo.add(mediaPlayerComponent, BorderLayout.CENTER);

        // Create a BlobServiceClient object which will be used to create a container client
        blobServiceClient = new BlobServiceClientBuilder().connectionString(connectionString).buildClient();
        // Create the container and return a container client object
        containerClient = blobServiceClient.getBlobContainerClient(containerName);
    }

    public void userLoggedIn(Usuari user) {
        this.usuari = user;
        pnlLoginContainer.removeAll();
        pnlLogin = null;
        pnlUserInfo = new UserInfoPanel(this, usuari);
        pnlLoginContainer.add(pnlUserInfo);
        repaint();
    }

    public void userLoggedOut() {
        this.usuari = null;
        pnlLoginContainer.removeAll();
        pnlUserInfo = null;
        pnlLogin = new LoginPanel(this);
        pnlLoginContainer.add(pnlLogin);
        repaint();
        //clear all other panels
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlLeft = new javax.swing.JPanel();
        pnlLoginContainer = new javax.swing.JPanel();
        btnShowRegisterDialog = new javax.swing.JButton();
        btnGetAttemptsToReview = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        pnlVideo = new javax.swing.JPanel();
        btnPause = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        spnValoracio = new javax.swing.JSpinner();
        jScrollPane2 = new javax.swing.JScrollPane();
        txaComentari = new javax.swing.JTextArea();
        btnInsertReview = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        pnlLeft.setLayout(null);

        pnlLoginContainer.setBorder(javax.swing.BorderFactory.createTitledBorder("Login"));
        pnlLoginContainer.setOpaque(false);
        pnlLoginContainer.setLayout(null);
        pnlLeft.add(pnlLoginContainer);
        pnlLoginContainer.setBounds(0, 10, 300, 300);
        pnlLoginContainer.getAccessibleContext().setAccessibleDescription("");

        getContentPane().add(pnlLeft);
        pnlLeft.setBounds(0, 0, 300, 300);

        btnShowRegisterDialog.setText("Register");
        btnShowRegisterDialog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowRegisterDialogActionPerformed(evt);
            }
        });
        getContentPane().add(btnShowRegisterDialog);
        btnShowRegisterDialog.setBounds(350, 20, 73, 23);

        btnGetAttemptsToReview.setText("Get attempts to review");
        btnGetAttemptsToReview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGetAttemptsToReviewActionPerformed(evt);
            }
        });
        getContentPane().add(btnGetAttemptsToReview);
        btnGetAttemptsToReview.setBounds(50, 320, 190, 23);
        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(10, 350, 300, 230);

        pnlVideo.setBorder(javax.swing.BorderFactory.createTitledBorder("Video player"));
        pnlVideo.setLayout(new java.awt.BorderLayout());
        getContentPane().add(pnlVideo);
        pnlVideo.setBounds(500, 60, 500, 390);

        btnPause.setText("Pause");
        btnPause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPauseActionPerformed(evt);
            }
        });
        getContentPane().add(btnPause);
        btnPause.setBounds(700, 460, 72, 23);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Review"));

        spnValoracio.setModel(new javax.swing.SpinnerNumberModel(0, null, 5, 1));
        jPanel1.add(spnValoracio);

        txaComentari.setColumns(20);
        txaComentari.setRows(5);
        jScrollPane2.setViewportView(txaComentari);

        jPanel1.add(jScrollPane2);

        btnInsertReview.setText("Send");
        btnInsertReview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertReviewActionPerformed(evt);
            }
        });
        jPanel1.add(btnInsertReview);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(500, 500, 500, 110);
        getContentPane().add(jProgressBar1);
        jProgressBar1.setBounds(840, 40, 146, 20);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lstIntentsValueChanged(ListSelectionEvent evt) {
        // https://stackoverflow.com/questions/12461627/jlist-fires-valuechanged-twice-when-a-value-is-changed-via-mouse
        if (!evt.getValueIsAdjusting()) {
            return;
        }

        Intent selectedIntent = lstIntents.getSelectedValue();

        if (selectedIntent == null) {
            return;
        }

        downloadThread = new Thread(this);
        downloadThread.start();
    }

    @Override
    public void run() {
        //System.out.println(Thread.currentThread().getName());
        downloadVideo();
    }

    private void downloadVideo() {
        // Downloading big images in chunks of 1kB might be very slow because of the request overhead to azure. Modify the algorithm to donwload eavery image in, for instance 20 chunks.
        Intent selectedIntent = lstIntents.getSelectedValue();
        if (selectedIntent == null) {
            return;
        }
        ByteArrayOutputStream outputStream;
        try {
            String blobName = "uploaded_user_videos/" + selectedIntent.getVideofile();
            BlockBlobClient blobClient = containerClient.getBlobClient(blobName).getBlockBlobClient();
            
            //Could use too...
            //blobClient.downloadToFile("path_to_file");
            
            int dataSize = (int) blobClient.getProperties().getBlobSize();
//            int numberOfBlocks = dataSize / 1024;
            int numberOfBlocks = 20;
            int numberOfBPerBlock = dataSize / numberOfBlocks;  // Split every image in 20 blocks. That is, make 20 requests to Azure.
            //System.out.println("Starting download of " + dataSize + " bytes in " + numberOfBlocks + " " + numberOfBPerBlock / 1024 + "kB chunks");

            int i = 0;
            outputStream = new ByteArrayOutputStream(dataSize);

            while (i < numberOfBlocks) {
                BlobRange range = new BlobRange(i * numberOfBPerBlock, (long) numberOfBPerBlock);
                DownloadRetryOptions options = new DownloadRetryOptions().setMaxRetryRequests(5);

                System.out.println(i + ": Downloading bytes " + range.getOffset() + " to " + (range.getOffset() + range.getCount()) + " with status "
                        + blobClient.downloadStreamWithResponse(outputStream, range, options, null, false,
                                Duration.ofSeconds(30), Context.NONE));
                i++;
                jProgressBar1.setValue(i * jProgressBar1.getMaximum() / (numberOfBlocks + 1));
            }

            // Download the last bytes of the image
            BlobRange range = new BlobRange(i * numberOfBPerBlock);
            DownloadRetryOptions options = new DownloadRetryOptions().setMaxRetryRequests(5);
            System.out.println(i + ": Downloading bytes " + range.getOffset() + " to " + dataSize + " with status "
                    + blobClient.downloadStreamWithResponse(outputStream, range, options, null, false,
                            Duration.ofSeconds(30), Context.NONE));
            i++;
            jProgressBar1.setValue(i * jProgressBar1.getMaximum() / (numberOfBlocks + 1));

//            blobClient.downloadStream(outputStream);  // Thread Blocking
            String appDataFolderPath = System.getenv("LOCALAPPDATA");
            File videoFile = new File(appDataFolderPath + "\\Temp\\Simulap\\videos\\" + selectedIntent.getVideofile());
            OutputStream fileOutputStream = new FileOutputStream(videoFile);
            ((ByteArrayOutputStream) outputStream).writeTo(fileOutputStream);

            outputStream.close();
            pnlVideo.setBorder(javax.swing.BorderFactory.createTitledBorder("Video player - " + videoFile.getName()));
            mediaPlayerComponent.mediaPlayer().media().play(videoFile.getAbsolutePath());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void refreshListOfPendingReviews() {
        lstIntents.removeAll();
        ArrayList<Intent> intents = dataAccess.getAttemptsPendingReview();
        DefaultListModel<Intent> dlmi = new DefaultListModel<>();
        for (Intent i : intents) {
            dlmi.addElement(i);
        }
        lstIntents.setModel(dlmi);
        //Unload video from mediaPlayer
        mediaPlayerComponent.mediaPlayer().media().startPaused("", "");
        pnlVideo.setBorder(javax.swing.BorderFactory.createTitledBorder("Video player"));
        //Reset review fields
        spnValoracio.setValue(0);
        try {
            spnValoracio.commitEdit();
        } catch (ParseException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        txaComentari.setText("");

    }

    private void btnShowRegisterDialogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowRegisterDialogActionPerformed
        RegisterDialog registerDialog = new RegisterDialog(this, true);
        registerDialog.setVisible(true);
    }//GEN-LAST:event_btnShowRegisterDialogActionPerformed

    private void btnGetAttemptsToReviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGetAttemptsToReviewActionPerformed
        refreshListOfPendingReviews();

    }//GEN-LAST:event_btnGetAttemptsToReviewActionPerformed

    private void btnPauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPauseActionPerformed
        if (isPlaying) {
            mediaPlayerComponent.mediaPlayer().controls().pause();
        } else {
            mediaPlayerComponent.mediaPlayer().controls().play();
        }
    }//GEN-LAST:event_btnPauseActionPerformed

    private void btnInsertReviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertReviewActionPerformed
        Review review = new Review();
        review.setIdIntent(lstIntents.getSelectedValue().getId());
        review.setIdReviewer(usuari.getId());
        try {
            spnValoracio.commitEdit();
        } catch (ParseException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        review.setValoracio((Integer) spnValoracio.getValue());
        review.setComentari(txaComentari.getText());
        int newReviewId = dataAccess.insertReview(review);
        if (newReviewId > 0) {
            JOptionPane.showMessageDialog(this, "Review successfully inserted!");
            // Refresh lstReview
            refreshListOfPendingReviews();
        }
    }//GEN-LAST:event_btnInsertReviewActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGetAttemptsToReview;
    private javax.swing.JButton btnInsertReview;
    private javax.swing.JButton btnPause;
    private javax.swing.JButton btnShowRegisterDialog;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel pnlLeft;
    private javax.swing.JPanel pnlLoginContainer;
    private javax.swing.JPanel pnlVideo;
    private javax.swing.JSpinner spnValoracio;
    private javax.swing.JTextArea txaComentari;
    // End of variables declaration//GEN-END:variables

    public Usuari getUsuari() {
        return usuari;
    }

    public void setUsuari(Usuari usuari) {
        this.usuari = usuari;
    }
}
