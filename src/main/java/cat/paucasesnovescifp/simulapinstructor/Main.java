package cat.paucasesnovescifp.simulapinstructor;

import cat.paucasesnovescifp.simulapinstructor.dataaccess.BlobDownloadWorker;
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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

/**
 *
 * @author Miguel
 */
public class Main extends javax.swing.JFrame {

    private Usuari usuari = null;
    private LoginPanel pnlLogin = null;
    private UserInfoPanel pnlUserInfo = null;
    private DataAccess dataAccess = null;
    private JList<Intent> lstIntents = new JList<>();
    private JComboBox<Usuari> cmbUsers = new JComboBox<>();
    private final EmbeddedMediaPlayerComponent mediaPlayerComponent;
    private JFileChooser fileChooser;
    private boolean isPlaying = false;

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

        cmbUsers.setBounds(310, 80, 140, 22);
        getContentPane().add(cmbUsers);
        DefaultComboBoxModel<Usuari> dcbm = new DefaultComboBoxModel<>();
        var usuaris = dataAccess.getAllUsers();
        for (Usuari u : usuaris) {
            dcbm.addElement(u);
        }
        cmbUsers.setModel(dcbm);

        cmbUsers.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent evt) {
                onCmbUsersItemStateChanged(evt);
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

//        // Create a BlobServiceClient object which will be used to create a container client
//        blobServiceClient = new BlobServiceClientBuilder().connectionString(connectionString).buildClient();
//        // Create the container and return a container client object
//        containerClient = blobServiceClient.getBlobContainerClient(containerName);
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
        pnlReview = new javax.swing.JPanel();
        spnValoracio = new javax.swing.JSpinner();
        jScrollPane2 = new javax.swing.JScrollPane();
        txaComentari = new javax.swing.JTextArea();
        btnInsertReview = new javax.swing.JButton();
        btnUpdateReview = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();
        statusLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        pnlLeft.setLayout(null);

        pnlLoginContainer.setBorder(javax.swing.BorderFactory.createTitledBorder("Login"));
        pnlLoginContainer.setOpaque(false);
        pnlLoginContainer.setLayout(null);
        pnlLeft.add(pnlLoginContainer);
        pnlLoginContainer.setBounds(0, 10, 300, 270);
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
        btnShowRegisterDialog.setBounds(310, 20, 72, 23);

        btnGetAttemptsToReview.setText("Get attempts to review");
        btnGetAttemptsToReview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGetAttemptsToReviewActionPerformed(evt);
            }
        });
        getContentPane().add(btnGetAttemptsToReview);
        btnGetAttemptsToReview.setBounds(50, 300, 190, 23);
        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(10, 350, 290, 230);

        pnlVideo.setBorder(javax.swing.BorderFactory.createTitledBorder("Video player"));
        pnlVideo.setLayout(new java.awt.BorderLayout());
        getContentPane().add(pnlVideo);
        pnlVideo.setBounds(490, 70, 510, 390);

        btnPause.setText("Pause");
        btnPause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPauseActionPerformed(evt);
            }
        });
        getContentPane().add(btnPause);
        btnPause.setBounds(920, 470, 72, 23);

        pnlReview.setBorder(javax.swing.BorderFactory.createTitledBorder("Review"));

        spnValoracio.setModel(new javax.swing.SpinnerNumberModel(0, null, 5, 1));
        pnlReview.add(spnValoracio);

        txaComentari.setColumns(20);
        txaComentari.setRows(5);
        jScrollPane2.setViewportView(txaComentari);

        pnlReview.add(jScrollPane2);

        btnInsertReview.setText("Send");
        btnInsertReview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertReviewActionPerformed(evt);
            }
        });
        pnlReview.add(btnInsertReview);

        btnUpdateReview.setText("Update");
        btnUpdateReview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateReviewActionPerformed(evt);
            }
        });
        pnlReview.add(btnUpdateReview);

        getContentPane().add(pnlReview);
        pnlReview.setBounds(500, 500, 500, 110);
        getContentPane().add(jProgressBar1);
        jProgressBar1.setBounds(840, 40, 146, 20);

        statusLabel.setText("jLabel1");
        getContentPane().add(statusLabel);
        statusLabel.setBounds(540, 470, 320, 16);

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

        String blobName = "uploaded_user_videos/" + selectedIntent.getVideofile();
        String appDataFolderPath = System.getenv("LOCALAPPDATA");
        File videoFile = new File(appDataFolderPath + "\\Temp\\Simulap\\videos\\" + selectedIntent.getVideofile());

        BlobDownloadWorker worker = new BlobDownloadWorker(jProgressBar1, statusLabel, blobName,
                videoFile);
        worker.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("progress".equals(evt.getPropertyName())) {
                    jProgressBar1.setValue((Integer) evt.getNewValue());
                } else if ("state".equals(evt.getPropertyName())) {
                    if (evt.getNewValue() == SwingWorker.StateValue.DONE) {
                        onDownloadFinished();
                    }
                }
            }
        });
        // Execute the worker in a Swing thread
        worker.execute();

        var selectedIntentReview = dataAccess.getAttemptReview(selectedIntent.getId());
        if (selectedIntentReview == null) {
            return;
        }
        spnValoracio.setValue(selectedIntentReview.getValoracio());
        txaComentari.setText(selectedIntentReview.getComentari());
        //Disable btnSendReview and enable btnUpdateReview
    }

    public void onDownloadFinished() {
        // This method will be called when the download is finished
        statusLabel.setText("Download finished!");
        Intent selectedIntent = lstIntents.getSelectedValue();
        String appDataFolderPath = System.getenv("LOCALAPPDATA");
        File videoFile = new File(appDataFolderPath + "\\Temp\\Simulap\\videos\\" + selectedIntent.getVideofile());
        pnlVideo.setBorder(javax.swing.BorderFactory.createTitledBorder("Video player - " + videoFile.getName()));
        mediaPlayerComponent.mediaPlayer().media().start(videoFile.getAbsolutePath());
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

    private void onCmbUsersItemStateChanged(ItemEvent evt) {
        Usuari selectedUser = (Usuari) cmbUsers.getSelectedItem();
        ArrayList<Intent> intents = dataAccess.getAttemptsPerUser(selectedUser);
        DefaultListModel<Intent> dlm = new DefaultListModel<>();
        for (Intent i : intents) {
            dlm.addElement(i);
        }
        //lstIntentsPerUser.setModel(dlm);
        lstIntents.removeAll();
        lstIntents.setModel(dlm);
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

    private void btnUpdateReviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateReviewActionPerformed
        Review review = dataAccess.getAttemptReview(lstIntents.getSelectedValue().getId());
        if (review == null) {
            return;
        }
        review.setValoracio((int) spnValoracio.getValue());
        review.setComentari(txaComentari.getText());
        int result = dataAccess.updateReview(review);
    }//GEN-LAST:event_btnUpdateReviewActionPerformed

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
    private javax.swing.JButton btnUpdateReview;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel pnlLeft;
    private javax.swing.JPanel pnlLoginContainer;
    private javax.swing.JPanel pnlReview;
    private javax.swing.JPanel pnlVideo;
    private javax.swing.JSpinner spnValoracio;
    private javax.swing.JLabel statusLabel;
    private javax.swing.JTextArea txaComentari;
    // End of variables declaration//GEN-END:variables

    public Usuari getUsuari() {
        return usuari;
    }

    public void setUsuari(Usuari usuari) {
        this.usuari = usuari;
    }
}
