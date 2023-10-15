package cat.paucasesnovescifp.simulapinstructor;

import cat.paucasesnovescifp.simulapinstructor.dataaccess.DataAccess;
import cat.paucasesnovescifp.simulapinstructor.models.Intent;
import cat.paucasesnovescifp.simulapinstructor.models.Usuari;
import cat.paucasesnovescifp.simulapinstructor.views.LoginPanel;
import cat.paucasesnovescifp.simulapinstructor.views.RegisterDialog;
import cat.paucasesnovescifp.simulapinstructor.views.UserInfoPanel;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JList;
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
public class Main extends javax.swing.JFrame {

    private Usuari usuari = null;
    private LoginPanel pnlLogin = null;
    private UserInfoPanel pnlUserInfo = null;
    private DataAccess dataAccess = null;
    private JList<Intent> lstIntents = new JList<Intent>();
    private final EmbeddedMediaPlayerComponent mediaPlayerComponent;
    private JFileChooser fileChooser;
    private boolean isPlaying = false;

    /**
     * Creates new form Main
     */
    public Main() {
        initComponents();

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
        btnShowRegisterDialog.setBounds(350, 20, 72, 23);

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

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lstIntentsValueChanged(ListSelectionEvent evt) {
        Intent selectedIntent = lstIntents.getSelectedValue();
        String appDataFolderPath = System.getenv("LOCALAPPDATA");
        File videoFile = new File(appDataFolderPath + "\\Simulap\\videos\\" + selectedIntent.getVideofile());
        if (videoFile.exists()) {
            pnlVideo.setBorder(javax.swing.BorderFactory.createTitledBorder("Video player - " + videoFile.getName()));
            mediaPlayerComponent.mediaPlayer().media().play(videoFile.getAbsolutePath());
        } else {
            pnlVideo.setBorder(javax.swing.BorderFactory.createTitledBorder("Video player - invalid video file"));
        }
    }

    private void btnShowRegisterDialogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowRegisterDialogActionPerformed
        RegisterDialog registerDialog = new RegisterDialog(this, true);
        registerDialog.setVisible(true);
    }//GEN-LAST:event_btnShowRegisterDialogActionPerformed

    private void btnGetAttemptsToReviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGetAttemptsToReviewActionPerformed
        lstIntents.removeAll();
        ArrayList<Intent> intents = dataAccess.getAttemptsPendingReview();
        DefaultListModel<Intent> dlmi = new DefaultListModel<>();
        for (Intent i : intents) {
            dlmi.addElement(i);
        }
        lstIntents.setModel(dlmi);

    }//GEN-LAST:event_btnGetAttemptsToReviewActionPerformed

    private void btnPauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPauseActionPerformed
        if (isPlaying) {
            mediaPlayerComponent.mediaPlayer().controls().pause();
        } else {
            mediaPlayerComponent.mediaPlayer().controls().play();
        }
    }//GEN-LAST:event_btnPauseActionPerformed

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
    private javax.swing.JButton btnPause;
    private javax.swing.JButton btnShowRegisterDialog;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnlLeft;
    private javax.swing.JPanel pnlLoginContainer;
    private javax.swing.JPanel pnlVideo;
    // End of variables declaration//GEN-END:variables

    public Usuari getUsuari() {
        return usuari;
    }

    public void setUsuari(Usuari usuari) {
        this.usuari = usuari;
    }
}