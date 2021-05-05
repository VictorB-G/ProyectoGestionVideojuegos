package vistas;
import clases.Controlador;
import clases.Enum;
import clases.Enum.Pegi;
import modelos.Videojuego;
import excepciones.NoExisteVideojuegoException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Year;
import javax.swing.*;
public class VentanaSwing extends JFrame implements IVentana{  
    private Controlador controller;
    private Videojuego videojuego, videojuegoAnt;
    private int idEvento;
    private ImageIcon iiApp;
    //Todos los paneles que colocare en mi ventana
    private JPanel panelVentana, panelNavegacion, panelDatos, panelBusqueda;
    private JPanel panelAcciones, panelBotonesAcciones;
    private JPanel panelSiNo, panelGrupoRButton, panelLogo, panelSuperior;
    private JTabbedPane tbPanelPrincipal;
    
    //Los layouts que usaré en los distintos paneles
    private BorderLayout layoutPrincipal;
    private GridLayout layoutDatos, layoutBusqueda, layoutAcciones;
    private FlowLayout layoutNavegacion, layoutBotonesAcciones;
    private FlowLayout layoutSiNo, layoutGrupoRbutton;
    
    // Componentes que usaré como imagenes o iconos
    private ImageIcon iiAceptar, iiCancelar, iiAnterior, iiSiguiente, iiPrimero;
    private ImageIcon iiUltimo, iiAñadir, iiModificar, iiBorrar, iiLogo;
    
    //Componentes que usare en el panel de navegación
    private JButton jbtnAlPrimero, jbtnAlUltimo, jbtnAlAnterior, jbtnAlSiguiente;
    
    //Componentes que usaré en el panel de datos
    private JLabel jlIdJuego, jlTitulo, jlGenero, jlConsola, jlDesarrollador;
    private JLabel jlPegi, jlMultiplayer, jlPrecio, jlStock, jlAño, jlDescripcion;  
    private JTextField txtIdJuego, txtTitulo, txtGenero, txtAño, txtDesarrollador;
    private JTextField txtDescripcion;
    private DefaultComboBoxModel cbModelConsola;
    private JComboBox cbConsola;
    private JCheckBox chkbStock;
    private ButtonGroup btgMultiplayer;
    private JRadioButton rbMultiplayer1, rbMultiplayer2, rbMultiplayer3;
    private DefaultListModel dlmPegi;
    private List listPegi;
    private JSpinner jSpinnerPrecio;
    private SpinnerNumberModel spinnerModel;
    
    //Componentes que usaré en el panel de busqueda
    private JLabel jlBusquedaId, jlBusquedaTitulo;
    private JTextField txtBusquedaId, txtBusquedaTitulo;
    private JButton jbtnBuscar;
    
    //Componentes que usaré en el panel de modificacion
    private JButton jbtnAñadir, jbtnSi, jbtnNo, jbtnEliminar, jbtnModificar;
    
    //Constructor de la ventana
    public VentanaSwing(){
        instanciarObjetos();
        añadirAlPanel();
        añadirPropiedades();
        eventos();
        bloquearCampos();
        setToolTipText();
    }

    private void instanciarObjetos() {
        instanciarPaneles();
        instanciarLayouts();
        setLayouts();
        instanciarComponentesImagenes();
        instanciarComponentesPanelNavegacion();
        instanciarComponentesDatos();
        instanciarComponentesPanelBusqueda();
        instanciarComponentesPanelModificacion();
    }
    
    private void instanciarPaneles() {
        panelVentana = new JPanel();
        panelNavegacion = new JPanel();
        tbPanelPrincipal = new JTabbedPane();
        panelDatos = new JPanel();
        panelBusqueda = new JPanel();
        panelAcciones = new JPanel();
        panelBotonesAcciones = new JPanel();
        panelSiNo = new JPanel();
        panelGrupoRButton = new JPanel();
        panelLogo = new JPanel();
        panelSuperior = new JPanel(new GridLayout(2, 1,0,0));
    }
    
    private void instanciarLayouts(){
        layoutPrincipal = new BorderLayout(10, 10);
        layoutNavegacion = new FlowLayout(FlowLayout.CENTER, 0, 10);
        layoutDatos = new GridLayout(6, 4, 45,5);
        layoutBusqueda = new GridLayout(11, 1,0,10);
        layoutAcciones = new GridLayout(2, 1);
        layoutBotonesAcciones = new FlowLayout(FlowLayout.CENTER, 0, 10);
        layoutSiNo = new FlowLayout(FlowLayout.CENTER, 0, 10);
        layoutGrupoRbutton = new FlowLayout(FlowLayout.CENTER);
    }
    
    private void setLayouts(){
        panelVentana.setLayout(layoutPrincipal);
        panelNavegacion.setLayout(layoutNavegacion);
        panelDatos.setLayout(layoutDatos);
        panelDatos.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelBusqueda.setLayout(layoutBusqueda);
        panelBusqueda.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelAcciones.setLayout(layoutAcciones);
        panelBotonesAcciones.setLayout(layoutBotonesAcciones);
        panelSiNo.setLayout(layoutSiNo);
        panelGrupoRButton.setLayout(layoutGrupoRbutton);
    }
    
    private void instanciarComponentesImagenes() {
        iiAceptar = new ImageIcon(getClass().getResource("/gfx/aceptar.png"));
        iiCancelar = new ImageIcon(getClass().getResource("/gfx/cancelar.png"));
        iiAnterior = new ImageIcon(getClass().getResource("/gfx/anterior.png"));
        iiAñadir = new ImageIcon(getClass().getResource("/gfx/añadir.png"));
        iiBorrar = new ImageIcon(getClass().getResource("/gfx/borrar.png"));
        iiModificar = new ImageIcon(getClass().getResource("/gfx/modificar.png"));
        iiPrimero = new ImageIcon(getClass().getResource("/gfx/primero.png"));
        iiSiguiente = new ImageIcon(getClass().getResource("/gfx/siguiente.png"));
        iiUltimo = new ImageIcon(getClass().getResource("/gfx/ultimo.png"));
        iiApp = new ImageIcon(getClass().getResource("/gfx/appIcon.png"));
        iiLogo = new ImageIcon(getClass().getResource("/gfx/logo.png"));
    }
    
    private void instanciarComponentesPanelNavegacion() {
        jbtnAlPrimero = new JButton(new ImageIcon(iiPrimero.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
        jbtnAlAnterior = new JButton(new ImageIcon(iiAnterior.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
        jbtnAlSiguiente = new JButton(new ImageIcon(iiSiguiente.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
        jbtnAlUltimo = new JButton(new ImageIcon(iiUltimo.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
    }
    
    private void instanciarComponentesDatos() {
        jlIdJuego = new JLabel("Id. Juego: ");
        jlTitulo = new JLabel("\tTítulo: ");
        jlGenero = new JLabel("\tGénero: ");
        jlConsola = new JLabel("\tConsola: ");
        jlDesarrollador = new JLabel("\tDesarrollador: ");
        jlPegi = new JLabel("Pegi: ");
        listPegi = new List();
        // Añadir los Pegi a la lista
        for (Pegi pegi: Pegi.values()){
            listPegi.add(pegi.toString());
        }
        jlMultiplayer = new JLabel("Tipo de multijugador: ");
        btgMultiplayer = new ButtonGroup();
        rbMultiplayer1 = new JRadioButton("Local");
        rbMultiplayer1.setActionCommand("LOCAL");
        rbMultiplayer2 = new JRadioButton("Online");
        rbMultiplayer2.setActionCommand("ONLINE");
        rbMultiplayer3 = new JRadioButton("No");
        rbMultiplayer3.setActionCommand("NO");
        añadirBotonesMultiplayer();
        jlPrecio = new JLabel("Precio: ");
        jlStock = new JLabel("\tUds. en Stock: ");
        chkbStock = new JCheckBox();
        txtIdJuego = new JTextField();
        txtTitulo = new JTextField();
        txtGenero = new JTextField();
        txtDesarrollador = new JTextField();
        spinnerModel = new SpinnerNumberModel(0.01, 0.01, 120.0, 0.01);
        jSpinnerPrecio = new JSpinner(spinnerModel);
        cbModelConsola = new DefaultComboBoxModel<>(Enum.Consola.values());
        cbConsola = new JComboBox(cbModelConsola);
        jlDescripcion = new JLabel("Descripción: ");
        txtDescripcion = new JTextField();
        jlAño = new JLabel("Año: ");
        txtAño = new JTextField();
    }
    
    private void añadirBotonesMultiplayer() {
        btgMultiplayer.add(rbMultiplayer1);
        btgMultiplayer.add(rbMultiplayer2);
        btgMultiplayer.add(rbMultiplayer3);
    }
    
    private void instanciarComponentesPanelBusqueda() {
        jlBusquedaId = new JLabel("Buscar por ID de juego");
        txtBusquedaId = new JTextField();
        jlBusquedaTitulo = new JLabel("Busqueda por titulo");
        txtBusquedaTitulo = new JTextField();
        jbtnBuscar = new JButton("Buscar");
    }
    
    private void instanciarComponentesPanelModificacion() {
        jbtnAñadir = new JButton(new ImageIcon(iiAñadir.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        jbtnSi = new JButton(new ImageIcon(iiAceptar.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
        jbtnNo = new JButton(new ImageIcon(iiCancelar.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
        jbtnModificar = new JButton(new ImageIcon(iiModificar.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        jbtnEliminar = new JButton(new ImageIcon(iiBorrar.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
    }    
    
    private void añadirAlPanel() {
        añadirALaVentana();
        añadirAlPanelNavegacion();
        añadirAlPanelDatos();
        añadirAlPanelBusqueda();
        añadirAlTabbedPannel();
        añadirAlPanelModificacion();
    }

    private void añadirALaVentana() {
        panelVentana.add(panelSuperior, BorderLayout.PAGE_START);
        panelVentana.add(tbPanelPrincipal, BorderLayout.CENTER);
        panelVentana.add(panelAcciones, BorderLayout.PAGE_END);
        this.add(panelVentana);
    }
    
    private void añadirAlPanelNavegacion() {
        panelSuperior.add(panelLogo);
        panelSuperior.add(panelNavegacion);
        panelLogo.add(new JLabel(new ImageIcon(iiLogo.getImage().getScaledInstance(500, 80, Image.SCALE_SMOOTH))));
        panelNavegacion.add(jbtnAlPrimero);
        panelNavegacion.add(jbtnAlAnterior);
        panelNavegacion.add(jbtnAlSiguiente);
        panelNavegacion.add(jbtnAlUltimo);
    }

    private void añadirAlPanelDatos() {
        panelDatos.add(jlIdJuego);
        panelDatos.add(txtIdJuego);
        panelDatos.add(new JLabel());
        panelDatos.add(new JLabel());
        panelDatos.add(jlTitulo);
        panelDatos.add(txtTitulo);
        panelDatos.add(jlGenero);
        panelDatos.add(txtGenero);
        panelDatos.add(jlConsola);
        panelDatos.add(cbConsola);
        panelDatos.add(jlDesarrollador);
        panelDatos.add(txtDesarrollador);
        panelDatos.add(jlPegi);
        panelDatos.add(listPegi);
        panelDatos.add(jlMultiplayer);
        panelGrupoRButton.add(rbMultiplayer1);
        panelGrupoRButton.add(rbMultiplayer2);
        panelGrupoRButton.add(rbMultiplayer3);
        panelDatos.add(panelGrupoRButton);
        panelDatos.add(jlAño);
        panelDatos.add(txtAño);
        panelDatos.add(jlDescripcion);
        panelDatos.add(txtDescripcion);
        panelDatos.add(jlPrecio);
        panelDatos.add(jSpinnerPrecio);
        panelDatos.add(jlStock);
        panelDatos.add(chkbStock);
    }

    private void añadirAlPanelBusqueda() {
        panelBusqueda.add(jlBusquedaId);
        panelBusqueda.add(txtBusquedaId);
        panelBusqueda.add(jlBusquedaTitulo);
        panelBusqueda.add(txtBusquedaTitulo);
        panelBusqueda.add(jbtnBuscar);
    }

    private void añadirAlTabbedPannel() {
        tbPanelPrincipal.addTab("Datos", panelDatos);
        tbPanelPrincipal.addTab("Busqueda", panelBusqueda);
    }
        
    private void añadirAlPanelModificacion() {
        panelAcciones.add(panelBotonesAcciones);
        panelBotonesAcciones.add(jbtnAñadir);
        panelBotonesAcciones.add(jbtnModificar);
        panelBotonesAcciones.add(jbtnEliminar);
        
        panelAcciones.add(panelSiNo);
        panelSiNo.add(jbtnSi);
        panelSiNo.add(jbtnNo);
    } 
    
    private void añadirPropiedades() {
        setTitle("Videojuegos");
        setIconImage(iiApp.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }
    
    private void eventos(){
        jbtnAlPrimero.addActionListener((ActionEvent arg0) -> {
            idEvento = 5;
            try{
                controller.notificacion();
                setCamposVideojuego();
            } catch (Exception ex){
                error(ex);
            }
        });
        
        jbtnAlAnterior.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                idEvento = 6;
                try{
                    controller.notificacion();
                    setCamposVideojuego();
                } catch (Exception ex){
                    error(ex);
                }
            }
        });
        
        jbtnAlSiguiente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                idEvento = 7;
                try{
                    controller.notificacion();
                    setCamposVideojuego();
                } catch (Exception ex){
                    error(ex);
                }
            }
        });
        
        jbtnAlUltimo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                idEvento = 8;
                try {
                    controller.notificacion();
                    setCamposVideojuego();
                }catch (Exception ex){
                    error(ex);
                }
            }
        });
        
        jbtnAñadir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                videojuegoAnt = videojuego;
                idEvento = 0;
                jbtnSi.setVisible(true);
                jbtnNo.setVisible(true);
                vaciarCampos();
                desbloquearCampos();
            }
        });
        
        jbtnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (videojuego!=null){
                    videojuegoAnt = videojuego;
                    idEvento = 1;
                    jbtnSi.setVisible(true);
                    jbtnNo.setVisible(true);
                    desbloquearCampos();
                    bloquearCampoId();
                }
            }
        });
        
        jbtnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                idEvento = 2;
                if (videojuego!=null){
                    int confirmarDelete = JOptionPane.YES_NO_OPTION;    
                    int confirmacion = JOptionPane.showConfirmDialog(null, "¿Estas seguro de que quieres eliminarlo?", "ATENCIÓN",confirmarDelete);
                    if (confirmacion==0){
                        try {
                        controller.notificacion();
                        vaciarCampos();
                        videojuego=null;
                    } catch (Exception ex){
                        error(ex);
                    }
                    }
                }else{
                    try{
                        throw new NoExisteVideojuegoException("No existe ese videojuego");
                    }catch(Exception ex){
                        error(ex);
                    }
                } 
            }
        });
        
        jbtnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {  
                try{
                    if (!txtBusquedaId.getText().isBlank()) {
                        idEvento = 3;
                    } else if (!txtBusquedaTitulo.getText().isBlank()){
                        idEvento = 4;
                    } else if (txtBusquedaId.getText().isBlank() && txtBusquedaTitulo.getText().isBlank()){
                        throw new Exception("Recuerda añadir datos a los campos.");
                    }
                    controller.notificacion();  
                    tbPanelPrincipal.setSelectedIndex(0);
                    setCamposVideojuego();
                } catch (NumberFormatException ex){
                    ex = new NumberFormatException("Recuerda que el id es un campo númerico");
                    error(ex);              
                } catch (Exception ex){
                    error(ex);
                }finally{
                    limpiarCamposBusqueda();
                }   
            }
        });
        
        jbtnSi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try{
                    crearObjetoVideojuego();
                    controller.notificacion();
                    setCamposVideojuego();
                }catch(Exception ex){
                    error(ex);
                    videojuego = videojuegoAnt;
                }finally{  
                    setCamposVideojuego();
                    bloquearCampos();
                    jbtnSi.setVisible(false);
                    jbtnNo.setVisible(false);
                }
            }
        });
        
        jbtnNo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                videojuego = videojuegoAnt;
                try{
                    setCamposVideojuego();
                }catch (Exception ex){
                    error(ex);
                }finally{
                    bloquearCampos();
                    jbtnSi.setVisible(false);
                    jbtnNo.setVisible(false);
                }    
            }
        });
    }
    
    @Override
    public void setControlador(Controlador c) {
        controller=c;
    }

    @Override
    public void mostrar() {
        panelSuperior.setVisible(true);
        panelNavegacion.setVisible(true);
        panelVentana.setVisible(true);
        jbtnSi.setVisible(false);
        jbtnNo.setVisible(false);
        setVisible(true);
    }

    @Override
    public Videojuego getVideojuego() {
        return videojuego;
    }

    @Override
    public void setVideojuego(Videojuego juego) {
        videojuego = juego;
    }

    @Override
    public int getAccion() {
        return idEvento;
    }

    @Override
    public int getBusquedaId() {
        try{
            return Integer.parseInt(txtBusquedaId.getText());
        }catch (NumberFormatException ex){
            throw new NumberFormatException("El id debe ser un numero entero.");
        }
        
    }

    @Override
    public String getBusquedaTitulo() {
        return txtBusquedaTitulo.getText();
    }
    
    public void error(Exception ex) {
        JDialog err = new JDialog(this, "¡¡¡ERROR!!!");
        JLabel jlErrorMsg = new JLabel(ex.getMessage());
        err.add(jlErrorMsg);
        err.setVisible(true);
        err.pack();
        err.setLocationRelativeTo(null);
    }
    
    private void setCamposVideojuego(){
        if (videojuego!=null){
            txtIdJuego.setText(videojuego.getIdJuego()+"");
            txtTitulo.setText(videojuego.getTitulo());
            txtGenero.setText(videojuego.getGenero());
            cbConsola.setSelectedItem(videojuego.getConsola());
            txtDesarrollador.setText(videojuego.getDesarrollador());
            switch (videojuego.getPegi()) {
                case "Pegi 3":
                    listPegi.select(0);
                    break;
                case "Pegi 7":
                    listPegi.select(1);
                    break;
                case "Pegi 12":
                    listPegi.select(2);
                    break;
                case "Pegi 16":
                    listPegi.select(3);
                    break;
                case "Pegi 18":
                    listPegi.select(4);
                    break;
            }
            switch (videojuego.getMultijugador()) {
                case LOCAL:
                    rbMultiplayer1.setSelected(true);
                    break;
                case ONLINE:
                    rbMultiplayer2.setSelected(true);
                    break;
                case NO:
                    rbMultiplayer3.setSelected(true);
                    break;
            }
            txtAño.setText(videojuego.getAño().toString());
            txtDescripcion.setText(videojuego.getDescripcion());
            jSpinnerPrecio.setValue(videojuego.getPrecio());
            if (videojuego.isStock()){
                chkbStock.setSelected(true);
            }else{
                chkbStock.setSelected(false);
            }
        } else {
            vaciarCampos();
        }
    }
    
    private void vaciarCampos(){
        txtIdJuego.setText("");
        txtTitulo.setText("");
        txtGenero.setText("");
        txtDesarrollador.setText("");
        rbMultiplayer1.setSelected(false);
        rbMultiplayer2.setSelected(false);
        rbMultiplayer3.setSelected(false);
        txtAño.setText("");
        txtDescripcion.setText("");
        jSpinnerPrecio.setValue(0.01);
        chkbStock.setSelected(false);
    }
    
    private void desbloquearCampos(){
        txtIdJuego.setEditable(true);
        txtTitulo.setEditable(true);
        txtGenero.setEditable(true);
        cbConsola.setEnabled(true);
        txtDesarrollador.setEditable(true);
        listPegi.setEnabled(true);
        rbMultiplayer1.setEnabled(true);
        rbMultiplayer2.setEnabled(true);
        rbMultiplayer3.setEnabled(true);
        txtAño.setEditable(true);
        txtDescripcion.setEditable(true);
        jSpinnerPrecio.setEnabled(true);
        chkbStock.setEnabled(true);
    }
    
    private void bloquearCampos(){
        txtIdJuego.setEditable(false);
        txtTitulo.setEditable(false);
        txtGenero.setEditable(false);
        cbConsola.setEnabled(false);
        txtDesarrollador.setEditable(false);
        listPegi.setEnabled(false);
        rbMultiplayer1.setEnabled(false);
        rbMultiplayer2.setEnabled(false);
        rbMultiplayer3.setEnabled(false);
        txtAño.setEditable(false);
        txtDescripcion.setEditable(false);
        jSpinnerPrecio.setEnabled(false);
        chkbStock.setEnabled(false);
    }   

    private void bloquearCampoId(){
        txtIdJuego.setEditable(false);
    }
    
    private void crearObjetoVideojuego() throws Exception{
        try {
            videojuego = new Videojuego();
            videojuego.setIdJuego(Integer.parseInt(txtIdJuego.getText()));
            videojuego.setTitulo(txtTitulo.getText());
            videojuego.setGenero(txtGenero.getText());
            videojuego.setConsola((Enum.Consola) cbConsola.getSelectedItem());
            videojuego.setDesarrollador(txtDesarrollador.getText());
            videojuego.setPegi(listPegi.getSelectedItem());
            videojuego.setMultijugador(Enum.Multijugador.valueOf(btgMultiplayer.getSelection().getActionCommand()));
            videojuego.setAño(Year.of(Integer.parseInt(txtAño.getText())));
            videojuego.setDescripcion(txtDescripcion.getText());
            videojuego.setPrecio((double) jSpinnerPrecio.getValue());
            videojuego.setStock(chkbStock.isSelected());
        } catch (Exception ex) {
            throw new Exception("Has introducido algun dato invalido, recuerda que:\n"
                    + "  El ID debe ser un numero entero.  \n"
                    + "  El año debes ser un numero entero de 4 cifras.  \n"
                    + "  El PEGI y el tipo de multijugador son campos OBLIGATORIOS.");
        }           
    }
    
    private void limpiarCamposBusqueda(){
        txtBusquedaId.setText("");
        txtBusquedaTitulo.setText("");
    }
    
    private void setToolTipText(){
        txtIdJuego.setToolTipText("El Id debe ser un numero entero");
        txtAño.setToolTipText("El año debe ser un numero entero");
        jbtnSi.setToolTipText("Todos los campos excepto si hay unidades en stock son obligatorios");
    }
}