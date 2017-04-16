package de.inf_schauer.javaCvGui.xml;

import java.awt.Rectangle;
import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import de.inf_schauer.javaCvGui.data.Checkbox;
import de.inf_schauer.javaCvGui.form.Field;
import de.inf_schauer.javaCvGui.form.FieldType;
import de.inf_schauer.javaCvGui.form.Form;
import de.inf_schauer.javaCvGui.form.FormConfiguration;
import de.inf_schauer.javaCvGui.form.FormRegion;
import de.inf_schauer.javaCvGui.form.ObjectFactory;
import de.inf_schauer.javaCvGui.form.Question;
import de.inf_schauer.javaCvGui.form.ResponseType;
import de.inf_schauer.javaCvGui.imageProcessing.Conversions;

public class Unmarshalling {
	
	//private String srcXSD = "C:\\Users\\chsc\\Documents\\Helmholtz\\xsd\\FormConfiguration.xsd";
	
	
	public static List<Checkbox> getCheckboxes(File src)
	{
		List<Checkbox> result = new ArrayList<Checkbox>();
		int ppi = 96;
		
		try {
			String c = FormConfiguration.class.getName();
			JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
			Unmarshaller u = jc.createUnmarshaller();

			JAXBElement<FormConfiguration> je = (JAXBElement<FormConfiguration>) u.unmarshal(src);
			FormConfiguration fc = je.getValue();
			
			List<Form> forms = fc.getForms();
			
			for(Form f : forms)
			{
				List<Question> questions = 	f.getQuestions();
				for(Question q : questions)
				{
					List<Field> fields = q.getFields();
					for(Field fi : fields)
					{
						boolean isCheckbox = fi.getType().equals(FieldType.CHECKBOX);
						
//						System.out.println("VALUE ID: " + q.getId() 
//						+ "; field: " + fi.getId() 
//						+ "; Type: " + fi.getType()
//						+"; isCheckbox: " + isCheckbox);
						
						if(isCheckbox)
						{
							//FormRegion fr = fi.getFormRegion();
							//System.out.println("Topleft " + fi.getId() + ": " + fr.getTopLeftX());
							FormRegion fr = fi.getFormRegion();
							BigInteger tx = fr.getTopLeftX();
							BigInteger ty = fr.getTopLeftY();
							BigInteger bx = fr.getBottomRightX();
							BigInteger by = fr.getBottomRightY();
							
							int width = bx.intValue() - tx.intValue();
							int height = by.intValue() - ty.intValue();
							
							Rectangle rect = new Rectangle(tx.intValue(), ty.intValue(), width, height);					
							result.add(new Checkbox(rect, fi.getId()));
						}
					}
					
				}
			}
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

}
