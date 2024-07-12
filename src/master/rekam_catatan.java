/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package master;
//import static com.teamdev.jxbrowser.deps.org.checkerframework.checker.units.qual.Prefix.peta;
import fungsi.Menuku;
import static fungsi.Menuku.label21;
import fungsi.koneksi;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
//import com.toedter.calendar.JDateChooser;
import java.text.ParseException;
import java.util.Date;
import javax.swing.ImageIcon;

/**
 *
 * @author USER
 */
public class rekam_catatan extends javax.swing.JFrame {

    private String SQL;
    /**
     * Creates new form rekam_catatan
     */
    public rekam_catatan() {
        initComponents();
        tampil_detail();
        setDefaultTable();
        tabelcatatan.setModel(tblModel);
        Tabel(tabelcatatan,new int[]{80,100,100,80,90,80,90});
        mati();
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        java.awt.Dimension dialogSize = getSize();
        setLocation((screenSize.width-dialogSize.width)/2,(screenSize.height-dialogSize.height)/2);
        ImageIcon ico = new ImageIcon("src/icon/patient.png");
        setIconImage(ico.getImage());
    }
    
    public void setDefaultTable() {
        String hubung = "jdbc:mysql://localhost:3306/rekamjejak_gunawan";
        try {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        Connection kon = DriverManager.getConnection(hubung,"root","");
        Statement stt = kon.createStatement();
        String SQL = "Select * from tb_catatan";
//        String SQL = "Select * from tb_catatan where id_pengguna='"+label21.getText()+"'";
         ResultSet res = stt.executeQuery(SQL);
        while(res.next()) {
        data[0] = res.getString(1);
        data[1] = res.getString(2);
        data[2] = res.getString(3);
        data[3] = res.getString(4);
        data[4] = res.getString(5);
        data[5] = res.getString(6);
        tblModel.addRow(data);
         }
        res.close();
        stt.close();
        kon.close();
        } catch(Exception exc) {
        System.err.println(exc.getMessage());
        }
    }

    private javax.swing.table.DefaultTableModel tblModel = getDefaultTabelModel();
    
    private void Tabel(javax.swing.JTable tb, int lebar[]) {
        tb.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        int kolom=tb.getColumnCount();
        for(int i=0;i < kolom;i++) {
        javax.swing.table.TableColumn tbc = tb.getColumnModel().getColumn(i);
        tbc.setPreferredWidth(lebar[i]);
        tb.setRowHeight(17);
        }
        }
        private javax.swing.table.DefaultTableModel getDefaultTabelModel() {
        return new javax.swing.table.DefaultTableModel(
        new Object [][]{},
        new String []{"No Catatan","No Pengguna","Tanggal","Jam","Lokasi","Suhu Tubuh"}
        ){
        boolean[] canEdit = new boolean []{
        false,false,false,false,false,false,false
        };
        public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit [columnIndex];
        }
        };
    }
        
    public void ref_nama(){
        String hubung = "jdbc:mysql://localhost:3306/rekamjejak_gunawan";
        try
        {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection kon = DriverManager.getConnection(hubung,"root","");
        Statement stt = kon.createStatement();
        // SQL = "SELECT nama_pengguna FROM tb_pengguna WHERE id_pengguna='"+idpengguna.getSelectedItem()+"'";
        SQL = "select id_pengguna from tb_pengguna";
        ResultSet rs = stt.executeQuery(SQL);
        while(rs.next())
        {
        // nama.setText(rs.getString(3));
        }
        }
        catch(Exception e)
        {
        System.out.print(e);
        }
    }
    
    public void cleanTable() {
    int baris = tblModel.getRowCount();
    for(int a=0;a<baris;a++) {
    tblModel.removeRow(0);
        }
    }
    
    public void tampil_detail(){
        String hubung="jdbc:mysql://localhost:3306/rekamjejak_gunawan";
        try{
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        Connection con=DriverManager.getConnection(hubung,"root","");
        Statement stt=con.createStatement();
        String sql = "Select id_pengguna from tb_pengguna order by id_pengguna asc";
        ResultSet res= stt.executeQuery(sql);
        while(res.next()){
        Object[] ob = new Object[1];
        ob[0] = res.getString(1);
        // idpengguna.addItem(ob[0]);
        }
        res.close();
        stt.close();
        con.close();
        } catch(Exception exc){
        System.err.println(exc.getMessage());}
    }
    
    public void autokode(){
        String hubung = "jdbc:mysql://localhost/rekamjejak_gunawan";

    try {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        Connection kon = DriverManager.getConnection(hubung, "root", "");
        Statement stmt = kon.createStatement();
        String SQL = "SELECT IFNULL(MAX(CAST(RIGHT(no_catatan, 3) AS SIGNED)), 0) AS no FROM tb_catatan";
        ResultSet rs = stmt.executeQuery(SQL);
        if (rs.next()) {
            int auto_nis = rs.getInt("no") + 1;
            String no = String.valueOf(auto_nis);
            idcatatan.setText(no);
        }
        rs.close();
        stmt.close();
    } catch (Exception e) {
        e.printStackTrace(); // Handle the exception appropriately, e.g., logging it.
    }
    }
    
    int row=0;
    public void tampil() {
        row = tabelcatatan.getSelectedRow();
        idcatatan.setText(tblModel.getValueAt(row,0).toString());
        idpengguna.setText(tblModel.getValueAt(row, 1).toString());
        // Get the date as a String from the table model
        String dateString = tblModel.getValueAt(row, 2).toString();

        try {
            // Parse the String into a Date using SimpleDateFormat
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Use the correct date format
            Date date = dateFormat.parse(dateString);

            // Set the parsed date in the JDateChooser
            tgcatatan.setDate(date);
        } catch (ParseException e) {
            // Handle parsing exception
            e.printStackTrace(); // Print or log the exception
        }
        jam.setText(tblModel.getValueAt(row, 3).toString());
        lokasi.setText(tblModel.getValueAt(row, 4).toString());
        suhutubuh.setText(tblModel.getValueAt(row, 5).toString());
        hidup();
    }
//    public void bersihtabel() {
//    int baris=tabelcatatan.getRowCount();
//    for(int a=0;a<baris;a++)
//    {
//    // tabelcatatan.removeRow(0);
//    }
//    }
    
    public void bersih() {
        idcatatan.setText(null);
        idpengguna.setText(null);
        tgcatatan.setDate(null);
        jam.setText(null);
        lokasi.setText(null);
        suhutubuh.setText(null);
    }
    
    public void mati() {
        idpengguna.setEnabled(false);
        tgcatatan.setEnabled(false);
        jam.setEnabled(false);
        lokasi.setEnabled(false);
        suhutubuh.setEnabled(false);
    }
    
    public void hidup() {
        idpengguna.setEnabled(true);
        tgcatatan.setEnabled(true);
        jam.setEnabled(true);
        lokasi.setEnabled(true);
        suhutubuh.setEnabled(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        idcatatan = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        idpengguna = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        tgcatatan = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jam = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        lokasi = new javax.swing.JTextArea();
        suhutubuh = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        b1 = new javax.swing.JButton();
        b2 = new javax.swing.JButton();
        b3 = new javax.swing.JButton();
        b4 = new javax.swing.JButton();
        b5 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelcatatan = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        tanggal = new com.toedter.calendar.JDateChooser();
        caritg = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(0, 0, 255));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fungsi/images.jpg"))); // NOI18N

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("NAMA");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("outputnama");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("NIK");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("outputnik");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("ID PENGGUNA");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("outputid");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(jLabel16)
                    .addComponent(jLabel15)
                    .addComponent(jLabel14)
                    .addComponent(jLabel13)
                    .addComponent(jLabel12))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addGap(26, 26, 26)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(153, 0, 153));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("NO CATATAN");

        idcatatan.setEnabled(false);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("ID PENGGUNA");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("TG CATATAN");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("JAM");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("LOKASI");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("SUHU TUBUH");

        lokasi.setColumns(20);
        lokasi.setRows(5);
        jScrollPane1.setViewportView(lokasi);

        gmap.setText("G-MAP");
        gmap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gmapActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("oC");

        b1.setText("Tambah");
        b1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b1ActionPerformed(evt);
            }
        });

        b2.setText("Simpan");
        b2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b2ActionPerformed(evt);
            }
        });

        b3.setText("Hapus");
        b3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b3ActionPerformed(evt);
            }
        });

        b4.setText("Ubah");
        b4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b4ActionPerformed(evt);
            }
        });

        b5.setText("Keluar");
        b5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b5ActionPerformed(evt);
            }
        });

        tabelcatatan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabelcatatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelcatatanMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelcatatan);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Cari Berdasarkan Tanggal");

        caritg.setText("Cari");
        caritg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                caritgActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(b1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(b2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(b3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(b4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(b5))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addGap(18, 18, 18)
                                    .addComponent(idpengguna, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(idcatatan, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel6)
                                        .addComponent(jLabel7))
                                    .addGap(24, 24, 24)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(tgcatatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jam, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel8))
                                .addGap(23, 23, 23)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(suhutubuh, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(14, 14, 14)
                                        .addComponent(jLabel10)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(gmap)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(tanggal, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                        .addGap(26, 26, 26)
                        .addComponent(caritg)
                        .addGap(144, 144, 144))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(idcatatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(idpengguna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(tgcatatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(suhutubuh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)))
                    .addComponent(gmap, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(b1)
                    .addComponent(b2)
                    .addComponent(b3)
                    .addComponent(b4)
                    .addComponent(b5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tanggal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(caritg, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );

        jPanel1.setBackground(new java.awt.Color(0, 0, 153));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("REKAM CATATAN PERJALANAN");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(294, 294, 294))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel4)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    String data[]=new String[7];
    private void gmapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gmapActionPerformed
        // TODO add your handling code here:
        new Peta().setVisible(true);
        lokasi.setText("SMK Negeri 1 Pasuruan\n" +"Jalan Veteran, Bugul Lor, Panggungrejo, Bugul Lor, Panggungrejo, Kota Pasuruan,Jawa Timur 67122");
    }//GEN-LAST:event_gmapActionPerformed

    private void b1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b1ActionPerformed
        // TODO add your handling code here:
        autokode();
        tgcatatan.requestFocus();
        hidup();
    }//GEN-LAST:event_b1ActionPerformed

    private void b2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b2ActionPerformed
        // TODO add your handling code here:
        String tampil = "yyyy-MM-dd";
        SimpleDateFormat format = new SimpleDateFormat(tampil);
        String tanggal = String.valueOf(format.format(tgcatatan.getDate()));
        String ObjButtons[] = {"Yes","No"};
        int pilihan = JOptionPane.showOptionDialog(null,"Apakah Anda Yakin Ingin Simpan Data?","Message",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,ObjButtons,ObjButtons[1]);
        if(pilihan == 0){
            try {
                String hubung="jdbc:mysql://localhost:3306/rekamjejak_gunawan";
                Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                Connection kon=DriverManager.getConnection(hubung,"root","");
                Statement stt=kon.createStatement();
                String SQL="insert into tb_catatan values('"+idcatatan.getText()+"','"+idpengguna.getText()+"','"+tanggal+"','"+jam.getText()+"','"+lokasi.getText()+"','"+suhutubuh.getText()+"')";
                stt.executeUpdate(SQL);
                data[0]=idcatatan.getText();
                data[1]=idpengguna.getText();
                data[2]=tanggal.toString();
                data[3]=jam.getText();
                data[4]=lokasi.getText();
                data[5]=suhutubuh.getText();
                tblModel.insertRow(0,data);
                stt.close();
                kon.close();
            }catch(Exception exc) { System.err.println(exc.getMessage());
            }
            bersih();
            mati();
            // tambah.setEnabled(true);
        }
    }//GEN-LAST:event_b2ActionPerformed

    private void b3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b3ActionPerformed
        // TODO add your handling code here:
        String ObjButtons[] = {"Yes","No"};
        int pilihan = JOptionPane.showOptionDialog(null,"Apakah Anda Yakin Ingin Hapus Data?","Message",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,ObjButtons,ObjButtons[1]);
        if(pilihan == 0){
            try {
                String hubung = "jdbc:mysql://localhost:3306/rekamjejak_gunawan";
                Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                Connection kon = DriverManager.getConnection(hubung,"root","");
                Statement stt = kon.createStatement();
                String SQL = "delete from tb_catatan where no_catatan='"+idcatatan.getText()+"'";
                stt.executeUpdate(SQL);
                tblModel.removeRow(row);
            } catch(Exception exc){
                System.err.println(exc.getMessage());
            }
            //            cleanTable();
            bersih();
            mati();
        }

    }//GEN-LAST:event_b3ActionPerformed

    private void b4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b4ActionPerformed
        // TODO add your handling code here:
        String tampil = "yyyy-MM-dd";
        SimpleDateFormat format = new SimpleDateFormat(tampil);
        String tanggal = String.valueOf(format.format(tgcatatan.getDate()));
        String ObjButtons[] = {"Yes","No"};
        int pilihan = JOptionPane.showOptionDialog(null,"Apakah Anda Yakin Ingin Ubah Data?","Message",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,ObjButtons,ObjButtons[1]);
        if(pilihan == 0){
            try{
                String hubung = "jdbc:mysql://localhost:3306/rekamjejak_gunawan";
                Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                Connection kon = DriverManager.getConnection(hubung,"root","");
                Statement stt = kon.createStatement();
                String SQL = "UPDATE tb_catatan SET id_pengguna = '" + idpengguna.getText() + "',"+ " tg_catatan = '" +tanggal+ "',"+ " jam = '" + jam.getText() + "',"+ " lokasi = '" + lokasi.getText() + "',"+ " suhu_tubuh = '" + suhutubuh.getText() + "'"+ " WHERE no_catatan = '" + idcatatan.getText() + "'";
                stt.executeUpdate(SQL);
                data[0] = idcatatan.getText();
                data[1] = idpengguna.getText();
                data[2] = tanggal.toString();
                data[3] = jam.getText();
                data[4] = lokasi.getText();
                data[5] = suhutubuh.getText();
                tblModel.removeRow(row);
                tblModel.insertRow(row, data);
                bersih();
                mati();
                stt.close();
                kon.close();
            }catch(Exception exc){
                System.err.println(exc.getMessage());
            }
        }
    }//GEN-LAST:event_b4ActionPerformed

    private void b5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b5ActionPerformed
        // TODO add your handling code here:
        new Menuku().setVisible(true);
        dispose();
    }//GEN-LAST:event_b5ActionPerformed

    private void tabelcatatanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelcatatanMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount()==2) {
            tampil();
            idcatatan.setEnabled(false);
        }
    }//GEN-LAST:event_tabelcatatanMouseClicked

    private void caritgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_caritgActionPerformed
        // TODO add your handling code here:
        String tampil = "yyyy-MM-dd";
        SimpleDateFormat format = new SimpleDateFormat(tampil);
        String tanggal1 = String.valueOf(format.format(tanggal.getDate()));
        cleanTable();
        String hubung="jdbc:mysql://localhost:3306/rekamjejak_gunawan";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection con=DriverManager.getConnection(hubung,"root","");
            Statement stt=con.createStatement();
            String sql = "Select * from tb_catatan WHERE tg_catatan ='"+tanggal1+"'";
            //        String sql = "Select * from tb_catatan where id_pengguna = '"+ idpengguna.getText() +"'" + "and tg_catatan = '" + tanggal + "'";
            ResultSet res= stt.executeQuery(sql);
            while(res.next()){
                // idcatatan.setText(res.getString(1));
                // idpengguna.setText(res.getString(2));
                // tgcatatan.setDate(res.getDate(3));
                // jam.setText(res.getString(4));
                // lokasi.setText(res.getString(5));
                // suhutubuh.setText(res.getString(6));
                //tblModel.removeRow(row);
                data[0] = res.getString(1);
                data[1] = res.getString(2);
                data[2] = res.getString(3);
                data[3] = res.getString(4);
                data[4] = res.getString(5);
                data[5] = res.getString(6);
                tblModel.addRow(data);
            }
            res.close();
            stt.close();
            con.close();
        } catch(Exception exc){
            System.err.println(exc.getMessage());
        }
    }//GEN-LAST:event_caritgActionPerformed

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
            java.util.logging.Logger.getLogger(rekam_catatan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(rekam_catatan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(rekam_catatan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(rekam_catatan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new rekam_catatan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton b1;
    private javax.swing.JButton b2;
    private javax.swing.JButton b3;
    private javax.swing.JButton b4;
    private javax.swing.JButton b5;
    private javax.swing.JButton caritg;
    public static final javax.swing.JButton gmap = new javax.swing.JButton();
    private javax.swing.JTextField idcatatan;
    private javax.swing.JTextField idpengguna;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jam;
    private javax.swing.JTextArea lokasi;
    private javax.swing.JTextField suhutubuh;
    private javax.swing.JTable tabelcatatan;
    private com.toedter.calendar.JDateChooser tanggal;
    private com.toedter.calendar.JDateChooser tgcatatan;
    // End of variables declaration//GEN-END:variables
}
