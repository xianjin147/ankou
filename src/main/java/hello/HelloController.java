package hello;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "*")
public class HelloController{

	
	@Autowired
	ResultRepository repo;
	
	
    
    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }


    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public String insert(@RequestParam(value = "code") String code) {
    	Result result =new Result(code);
    	result.setTimeUsed(0L);
    	repo.save(result);
        return "Greetings from Spring Boot!";
    }
    
    @RequestMapping("/remove")
    public String remove() {
        return "Greetings from Spring Boot!";
    }
    
    @RequestMapping("/list")
    public String list() {
    	List<Result> result = (List<Result>) repo.findAll();
        return result.toString();
    }
    
    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    public String verify(@RequestParam(value = "code") String code) {
    	Result r = null;
    	List<Result> rList = repo.findByCode(code);
    	r = rList.isEmpty() ? null : rList.get(0);
        return r!=null ? "Found": "Not Found";
    }
    
    
    @RequestMapping(value = "/verifyTest", method = RequestMethod.GET)
    public String verifyTest(@RequestParam(value = "code") String code) {
    	Result r = null;
    	List<Result> rList = repo.findByCode(code);
    	if(!rList.isEmpty()){
    		r = rList.get(0);
    		if(r.getTimeUsed() == null){
    			r.setTimeUsed(1L);
    		}else{
    			r.setTimeUsed(r.getTimeUsed()+1);
    		}
        	r = repo.save(r);
    	}
    	
        return r!=null ? "Found": "Not Found";
    }
    
    @SuppressWarnings("resource")
	@RequestMapping(value = "/initlist", method = RequestMethod.GET)
    public String initList() {
    	
    	List<Result> rList = new ArrayList<Result>();
        
        try {
        	File file = ResourceUtils.getFile("classpath:CODE.xlsx");
            FileInputStream excelFile = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();

            while (iterator.hasNext()) {

                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();

                while (cellIterator.hasNext()) {

                    Cell currentCell = cellIterator.next();
                    //getCellTypeEnum shown as deprecated for version 3.15
                    //getCellTypeEnum ill be renamed to getCellType starting from version 4.0
                    if (currentCell.getCellType() == Cell.CELL_TYPE_STRING) {
                        System.out.print(currentCell.getStringCellValue() + "--");
                        rList.add(new Result(currentCell.getStringCellValue()));
                    }

                }
                System.out.println();

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        repo.saveAll(rList);

        return list();
    }
    

    
    
}
