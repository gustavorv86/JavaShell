package frames;

import bsh.BshMethod;
import bsh.Interpreter;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

public class MainFrame extends javax.swing.JFrame {
    
    private final Interpreter shell;
    private final DefaultTableModel modelVars;
    private final DefaultTableModel modelMethods;
    private final RSyntaxTextArea syntaxTextArea;
    
    private final ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
    private final ByteArrayOutputStream systemErr = new ByteArrayOutputStream();

    public MainFrame() {
        initComponents();
        this.setLocationRelativeTo(null);
        // this.setExtendedState( getExtendedState()|JFrame.MAXIMIZED_BOTH );
        this.shell = new Interpreter();
        this.modelVars = (DefaultTableModel) this.jTableVars.getModel();
        this.modelMethods = (DefaultTableModel) this.jTableMethods.getModel();
        this.syntaxTextArea = new RSyntaxTextArea();
        this.initEditor(syntaxTextArea);
        this.updateVars();
        // redireccionar las salidas
        System.setOut(new PrintStream(systemOut));
        System.setErr(new PrintStream(systemErr));
        
        String str = null;
    }

    private void initEditor(RSyntaxTextArea syntaxTextArea) {
        syntaxTextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        syntaxTextArea.setCodeFoldingEnabled(true);
        syntaxTextArea.setText("/* Add your Java code here... */\n");
        syntaxTextArea.setCaretPosition(0);

        RTextScrollPane scrollPaneEditor = new RTextScrollPane(syntaxTextArea);
        
        javax.swing.GroupLayout jPanelEditorLayout = new javax.swing.GroupLayout(jPanelEditor);
        jPanelEditor.setLayout(jPanelEditorLayout);
        jPanelEditorLayout.setHorizontalGroup(
            jPanelEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPaneEditor)
        );
        jPanelEditorLayout.setVerticalGroup(
            jPanelEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPaneEditor, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
        );
    }

    private void submitButton(){
        String content = syntaxTextArea.getText();
        try {
            // evaluar expresion
            Object resul = shell.eval(content);
            // redireccionar salidas
            writeOutput(systemOut.toString(), Color.BLUE);
            writeOutput(systemErr.toString(), Color.RED);
            systemOut.reset();
            systemErr.reset();
            // mostrar resultado
            if(resul != null) {
                writeOutput(resul.toString(), Color.BLUE);
            }
            // borrar editor
            // syntaxTextArea.setText("");
        } catch (Exception ex) {
            writeOutput(ex.toString(), Color.RED);
        }
        writeOutput("\n", Color.BLUE);
        updateVars();
    }
    
    private void updateVars(){
        modelVars.setRowCount(0);
        for(String var : shell.getNameSpace().getVariableNames()) {
            Object value;
            try {
                value = shell.get(var);
            } catch (Exception ex) {
                value = ex.toString();
            }
            modelVars.addRow(new Object[]{var, value});
        }
        
        modelMethods.setRowCount(0);
        for(BshMethod method : shell.getNameSpace().getMethods()) {
            String name = method.getName();
            String args = "";
            for(Class type : method.getParameterTypes()) {
                args += ", "+type.toString();
            }
            if(args.isEmpty()) {
                args = "void";
            } else {
                args = args.substring(2);
            }
            String retr = method.getReturnType().toString();
            modelMethods.addRow(new String[]{name, args, retr});
        }        
        
    }
    
    private void writeOutput(String message, Color color) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = jTextPaneOutput.getDocument().getLength();
        
        jTextPaneOutput.setCaretPosition(len);
        jTextPaneOutput.setCharacterAttributes(aset, false);
        jTextPaneOutput.setEditable(true);
        jTextPaneOutput.replaceSelection(message);
        jTextPaneOutput.setEditable(false);
    }
    
    private void clearOutput(){
        this.jTextPaneOutput.setText("");
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPaneMain = new javax.swing.JSplitPane();
        jSplitPaneLeft = new javax.swing.JSplitPane();
        jPanelVars = new javax.swing.JPanel();
        jScrollPaneVars = new javax.swing.JScrollPane();
        jTableVars = new javax.swing.JTable();
        jPanelMethods = new javax.swing.JPanel();
        jScrollPaneMethods = new javax.swing.JScrollPane();
        jTableMethods = new javax.swing.JTable();
        jSplitPaneRight = new javax.swing.JSplitPane();
        jPanelContainerEditor = new javax.swing.JPanel();
        jPanelEditor = new javax.swing.JPanel();
        jButtonSubmit = new javax.swing.JButton();
        jPanelContainerOutput = new javax.swing.JPanel();
        jButtonClearOutput = new javax.swing.JButton();
        jScrollPaneOutput = new javax.swing.JScrollPane();
        jTextPaneOutput = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JavaShell");
        setBackground(new java.awt.Color(255, 255, 255));

        jSplitPaneMain.setBackground(new java.awt.Color(255, 255, 255));
        jSplitPaneMain.setDividerLocation(480);
        jSplitPaneMain.setPreferredSize(new java.awt.Dimension(1360, 640));

        jSplitPaneLeft.setBackground(new java.awt.Color(255, 255, 255));
        jSplitPaneLeft.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jPanelVars.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPaneVars.setBackground(new java.awt.Color(255, 255, 255));

        jTableVars.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Variables", "Values"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableVars.getTableHeader().setReorderingAllowed(false);
        jScrollPaneVars.setViewportView(jTableVars);

        javax.swing.GroupLayout jPanelVarsLayout = new javax.swing.GroupLayout(jPanelVars);
        jPanelVars.setLayout(jPanelVarsLayout);
        jPanelVarsLayout.setHorizontalGroup(
            jPanelVarsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelVarsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPaneVars, javax.swing.GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelVarsLayout.setVerticalGroup(
            jPanelVarsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelVarsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPaneVars, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPaneLeft.setTopComponent(jPanelVars);

        jPanelMethods.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPaneMethods.setBackground(new java.awt.Color(255, 255, 255));

        jTableMethods.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Methods", "Arguments", "Return"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableMethods.getTableHeader().setReorderingAllowed(false);
        jScrollPaneMethods.setViewportView(jTableMethods);

        javax.swing.GroupLayout jPanelMethodsLayout = new javax.swing.GroupLayout(jPanelMethods);
        jPanelMethods.setLayout(jPanelMethodsLayout);
        jPanelMethodsLayout.setHorizontalGroup(
            jPanelMethodsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 477, Short.MAX_VALUE)
            .addGroup(jPanelMethodsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelMethodsLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPaneMethods, javax.swing.GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanelMethodsLayout.setVerticalGroup(
            jPanelMethodsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 325, Short.MAX_VALUE)
            .addGroup(jPanelMethodsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelMethodsLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPaneMethods, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jSplitPaneLeft.setRightComponent(jPanelMethods);

        jSplitPaneMain.setLeftComponent(jSplitPaneLeft);

        jSplitPaneRight.setBackground(new java.awt.Color(255, 255, 255));
        jSplitPaneRight.setDividerLocation(380);
        jSplitPaneRight.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jPanelContainerEditor.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanelEditorLayout = new javax.swing.GroupLayout(jPanelEditor);
        jPanelEditor.setLayout(jPanelEditorLayout);
        jPanelEditorLayout.setHorizontalGroup(
            jPanelEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 852, Short.MAX_VALUE)
        );
        jPanelEditorLayout.setVerticalGroup(
            jPanelEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 323, Short.MAX_VALUE)
        );

        jButtonSubmit.setText("Submit");
        jButtonSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSubmitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelContainerEditorLayout = new javax.swing.GroupLayout(jPanelContainerEditor);
        jPanelContainerEditor.setLayout(jPanelContainerEditorLayout);
        jPanelContainerEditorLayout.setHorizontalGroup(
            jPanelContainerEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelContainerEditorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelContainerEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelEditor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelContainerEditorLayout.createSequentialGroup()
                        .addComponent(jButtonSubmit)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelContainerEditorLayout.setVerticalGroup(
            jPanelContainerEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelContainerEditorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelEditor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonSubmit)
                .addContainerGap())
        );

        jSplitPaneRight.setTopComponent(jPanelContainerEditor);

        jPanelContainerOutput.setBackground(new java.awt.Color(255, 255, 255));

        jButtonClearOutput.setText("Clear");
        jButtonClearOutput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClearOutputActionPerformed(evt);
            }
        });

        jScrollPaneOutput.setBackground(new java.awt.Color(255, 255, 255));

        jTextPaneOutput.setEditable(false);
        jScrollPaneOutput.setViewportView(jTextPaneOutput);

        javax.swing.GroupLayout jPanelContainerOutputLayout = new javax.swing.GroupLayout(jPanelContainerOutput);
        jPanelContainerOutput.setLayout(jPanelContainerOutputLayout);
        jPanelContainerOutputLayout.setHorizontalGroup(
            jPanelContainerOutputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelContainerOutputLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelContainerOutputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPaneOutput)
                    .addGroup(jPanelContainerOutputLayout.createSequentialGroup()
                        .addComponent(jButtonClearOutput)
                        .addGap(0, 795, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelContainerOutputLayout.setVerticalGroup(
            jPanelContainerOutputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelContainerOutputLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPaneOutput, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonClearOutput)
                .addContainerGap())
        );

        jSplitPaneRight.setRightComponent(jPanelContainerOutput);

        jSplitPaneMain.setRightComponent(jSplitPaneRight);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPaneMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPaneMain, javax.swing.GroupLayout.DEFAULT_SIZE, 648, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSubmitActionPerformed
        submitButton();
    }//GEN-LAST:event_jButtonSubmitActionPerformed

    private void jButtonClearOutputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClearOutputActionPerformed
        clearOutput();
    }//GEN-LAST:event_jButtonClearOutputActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonClearOutput;
    private javax.swing.JButton jButtonSubmit;
    private javax.swing.JPanel jPanelContainerEditor;
    private javax.swing.JPanel jPanelContainerOutput;
    private javax.swing.JPanel jPanelEditor;
    private javax.swing.JPanel jPanelMethods;
    private javax.swing.JPanel jPanelVars;
    private javax.swing.JScrollPane jScrollPaneMethods;
    private javax.swing.JScrollPane jScrollPaneOutput;
    private javax.swing.JScrollPane jScrollPaneVars;
    private javax.swing.JSplitPane jSplitPaneLeft;
    private javax.swing.JSplitPane jSplitPaneMain;
    private javax.swing.JSplitPane jSplitPaneRight;
    private javax.swing.JTable jTableMethods;
    private javax.swing.JTable jTableVars;
    private javax.swing.JTextPane jTextPaneOutput;
    // End of variables declaration//GEN-END:variables
}
