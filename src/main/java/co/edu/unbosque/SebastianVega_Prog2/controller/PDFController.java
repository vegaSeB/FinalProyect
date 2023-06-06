package co.edu.unbosque.SebastianVega_Prog2.controller;

import java.io.FileOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.swing.filechooser.FileSystemView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import co.edu.unbosque.SebastianVega_Prog2.model.Parte;
import co.edu.unbosque.SebastianVega_Prog2.model.Persona;
import co.edu.unbosque.SebastianVega_Prog2.model.Vehiculo;
import co.edu.unbosque.SebastianVega_Prog2.repository.ParteRepository;
import co.edu.unbosque.SebastianVega_Prog2.repository.PersonaRepository;
import co.edu.unbosque.SebastianVega_Prog2.repository.VehiculoRepository;
import jakarta.transaction.Transactional;

@Transactional
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/pdf")
public class PDFController {
	
	@Autowired
	VehiculoRepository verep;

	@Autowired
	PersonaRepository perrep;
	
	@Autowired
	ParteRepository parrep;
	
	@GetMapping("/vehiculos")
	public ResponseEntity<String> createPDFVehiculos() {
		
		try {
			Document doc = new Document();
			
			
			
			OutputStream outputStream = 
					new FileOutputStream(new File(FileSystemView.getFileSystemView().getHomeDirectory() + "/Vehiculos_Personas.pdf"));
			
			PdfWriter.getInstance(doc, outputStream);
			
			doc.open();
			
			PdfPTable table = new PdfPTable(3);
			añadirCabezalV(table);
			añadirFilasV(table);

			doc.add(table);
			doc.close();
	        outputStream.close();
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body("Archivo creado en el escritorio");
		
	}
	private void añadirCabezalV(PdfPTable table) {
	    Stream.of("Nombre del dueño", "Cedula del dueño", "Placas de los vehiculos")
	      .forEach(columnTitle -> {
	        PdfPCell header = new PdfPCell();
	        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        header.setBorderWidth(2);
	        header.setPhrase(new Phrase(columnTitle));
	        table.addCell(header);
	    });
	}
	
	private void añadirFilasV(PdfPTable table) {
		
		List<Persona> list = perrep.findAll();
		for(Persona aux: list) {
		table.addCell(aux.getNombre());
		table.addCell(""+aux.getCedula());
		List<Vehiculo> placas = verep.findByIdd(aux);
		String a = "";
		for(Vehiculo ve:placas) {
			a += ve.getPlaca()+ "\n";
		}
		table.addCell(a);
		}
		
	}
	
	@GetMapping("/partesP")
	public ResponseEntity<String> createPDFPartesP() {
		
		try {
			Document doc = new Document();
			
			OutputStream outputStream = 
					new FileOutputStream(new File(FileSystemView.getFileSystemView().getHomeDirectory() + "/Partes_Personas.pdf"));
			
			PdfWriter.getInstance(doc, outputStream);
			
			doc.open();
			
			PdfPTable table = new PdfPTable(4);
			añadirCabezalP(table);
			añadirFilasP(table);

			doc.add(table);
			doc.close();
	        outputStream.close();
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body("Archivo creado en el escritorio");
		
	}
	
	private void añadirCabezalP(PdfPTable table) {
	    Stream.of("Nombre del dueño", "Cedula del dueño", "Codigos de los partes", "Multa")
	      .forEach(columnTitle -> {
	        PdfPCell header = new PdfPCell();
	        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        header.setBorderWidth(2);
	        header.setPhrase(new Phrase(columnTitle));
	        table.addCell(header);
	    });
	}
	
	private void añadirFilasP(PdfPTable table) {
		
		List<Persona> list = perrep.findAll();
		for(Persona aux: list) {
		table.addCell(aux.getNombre());
		table.addCell(""+aux.getCedula());
		
		List<Vehiculo> placas = verep.findByIdd(aux);
		String codigos = "";
		String costos = "";
		
		for(Vehiculo ve:placas) {
			
			List<Parte> par = parrep.findByIdv(ve);
			
			if(!par.isEmpty()) {
				for(Parte p:par) {
				codigos += p.getCodigo() + "\n\n";
				costos += p.getMulta() + "\n";
				}
			}
			
		}
		
		table.addCell(codigos);
		table.addCell(costos);
		}
		
	}
	
	@GetMapping("/partes")
	public ResponseEntity<String> createPDFPartes() {
		
		try {
			Document doc = new Document();
			
			OutputStream outputStream = 
					new FileOutputStream(new File(FileSystemView.getFileSystemView().getHomeDirectory() + "/Partes.pdf"));
			
			PdfWriter.getInstance(doc, outputStream);
			
			doc.open();
			
			Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
			
			PdfPTable table = new PdfPTable(4);
			añadirCabezal(table);
			añadirFilas(table);
			
			doc.add(new Paragraph("Sistema de partes de la alcaldia", font));
			doc.add(new Paragraph("Total de ganancia en multas: " + calcularTotal(), font));
			doc.add(new Paragraph("  ", font));
			doc.add(table);
			doc.close();
	        outputStream.close();
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body("Archivo creado en el escritorio");
		
	}
	
	private void añadirCabezal(PdfPTable table) {
	    Stream.of("Id","Codigo del parte", "Multa", "Placa del auto")
	      .forEach(columnTitle -> {
	        PdfPCell header = new PdfPCell();
	        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        header.setBorderWidth(2);
	        header.setPhrase(new Phrase(columnTitle));
	        table.addCell(header);
	    });
	}
	
	private void añadirFilas(PdfPTable table) {
		
		List<Parte> list = parrep.findAll();
		
		for(Parte par:list) {
			
			table.addCell(""+par.getId());
			table.addCell(par.getCodigo());
			table.addCell(par.getMulta());
			table.addCell(par.getIdv().getPlaca());
			
		}
		
	}
	
	public String calcularTotal() {
		
		List<Parte> partes = parrep.findAll();
		int total = 0;
		
		for(Parte par:partes) {
			
			if(par.getMulta().contains("8 SMLDV")) {
				total += 8;
			}
			if(par.getMulta().contains("15 SMLDV")) {
				total += 15;
			}
			
		}
		
		return total + " SMLDV / $" + (total*34900);
		
	}
}
