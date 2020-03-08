package ljd.classmanager.Service.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import ljd.classmanager.Dao.DeptDao;
import ljd.classmanager.Dao.StudentsDao;
import ljd.classmanager.Entity.ClassEntity;
import ljd.classmanager.Entity.DeptEntity;
import ljd.classmanager.Entity.MajorEntity;
import ljd.classmanager.Entity.StudentsEntity;
import ljd.classmanager.Service.StudentsService;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @program: classmanager
 * @description:
 * @author: liu yan
 * @create: 2020-01-15 17:00
 */
@Service
public class StudentsServiceImpl implements StudentsService {
    @Autowired
    private StudentsDao studentsDao;
    @Override
    public List<StudentsEntity> getStuList(StudentsEntity studentsEntity) {
        return studentsDao.getStuList(studentsEntity);
    }

    @Override
    public List<StudentsEntity> getStuListBySnoAndSname(StudentsEntity studentsEntity) {
        return studentsDao.getStuListBySnoAndSname(studentsEntity);
    }

    @Override
    public List<StudentsEntity> getStuByClassCode(String ClassCode) {
        QueryWrapper<StudentsEntity> wrapper=new QueryWrapper<>();
        wrapper.eq("class_code",ClassCode);
        return studentsDao.selectList(wrapper);
    }

    @Override
    public Integer getStuCount(StudentsEntity studentsEntity) {
        return studentsDao.getStuCount(studentsEntity);
    }

    @Override
    public Integer addStudents(StudentsEntity studentsEntity) {
        return studentsDao.insert(studentsEntity);
    }

    @Override
    public Integer updateStudents(StudentsEntity studentsEntity) {
        UpdateWrapper<StudentsEntity> wrapper=new UpdateWrapper<>();
        wrapper.eq("s_no",studentsEntity.getsNo());
        return studentsDao.update(studentsEntity,wrapper);
    }

    @Override
    public Integer updateStuOpenId(StudentsEntity studentsEntity) {
        UpdateWrapper<StudentsEntity> wrapper=new UpdateWrapper<>();
        wrapper.eq("s_no",studentsEntity.getsNo());
        wrapper.set("s_openid",studentsEntity.getsOpenid());
        return studentsDao.update(studentsEntity,wrapper);
    }

    @Override
    public Integer delStudents(StudentsEntity studentsEntity) {
        QueryWrapper<StudentsEntity> wrapper=new QueryWrapper<>();
        wrapper.eq("s_no",studentsEntity.getsNo());
        return studentsDao.delete(wrapper);
    }

    @Override
    @Transactional
    public Map<String, Object> importExcel(MultipartFile xlsFile) {

        Map<String, Object> result = new HashMap<>();
        ArrayList<String> ErrorsList=new ArrayList<>();
        int success=0;
        int fail=0;
        Row row = null;
        // contentType
        // String contentType = file.getContentType();
        // excel文件名
        // String fileName = file.getOriginalFilename();
        if (xlsFile.isEmpty()) {
            result.put("code", 500);
            result.put("error", "导入文件为空！");
            return result;
        }
        // 根据不同excel创建不同对象,Excel2003版本-->HSSFWorkbook,Excel2007版本-->XSSFWorkbook
        Workbook wb = null;
        InputStream im = null;
        try {
            im = xlsFile.getInputStream();
            wb = WorkbookFactory.create(im);
            // 根据页面index 获取sheet页
            Sheet sheet = wb.getSheetAt(0);
            //获取表头，判断是否为导入模板表格
            row=sheet.getRow(1);
            if (row.getCell(1).getStringCellValue().equals("年级")
                    && row.getCell(2).getStringCellValue().equals("学号")
                    && row.getCell(3).getStringCellValue().equals("院系")
                    && row.getCell(4).getStringCellValue().equals("专业")
                    && row.getCell(5).getStringCellValue().equals("班级")
                    && row.getCell(6).getStringCellValue().equals("姓名")
                    && row.getCell(7).getStringCellValue().equals("性别")
                    && row.getCell(8).getStringCellValue().equals("联系方式")){
                // 循环sheet页中数据从第x行开始,例:第3行开始为导入数据
                for (int i = 2; i < sheet.getPhysicalNumberOfRows(); i++) {
                    StudentsEntity stuFor_xsl=new StudentsEntity();//新建一个学生对象
                    // 获取每一行数据
                    row = sheet.getRow(i);
                    if (isRowEmpty(row)){continue;}
                    row.getCell(0).setCellType(CellType.STRING);
                    // 如果表格内容为数字,需要设置CellType为string，否则调用getStringCellValue()会出现获取类型错误
                    row.getCell(1).setCellType(CellType.STRING);
                    if (row.getCell(1).toString().equals("")) {
                        fail+=1;
                        ErrorsList.add("序号为"+row.getCell(0).getStringCellValue()+"的数据导入失败，年级不能为空");
                        continue;
                    }else if (row.getCell(1).toString().matches("^[0-9]*$")&&row.getCell(1).toString().length()==4){
                        stuFor_xsl.setsGrade(row.getCell(1).getStringCellValue());
                        System.out.println(row.getCell(1));
                    }
                    else {
                        fail+=1;
                        System.out.println(row.getCell(1).toString()+" :"+row.getCell(1).toString().length());
                        ErrorsList.add("序号为"+row.getCell(0).getStringCellValue()+"的数据导入失败，请检查年级的格式");
                        continue;//如果不为数字或年份超过4位数，跳过此数据
                    }
                    if (row.getCell(2).toString().equals("")) {
                        row.getCell(0).setCellType(CellType.STRING);
                        fail+=1;
                        ErrorsList.add("序号为"+row.getCell(0).getStringCellValue()+"的数据导入失败，学号不能为空");
                        continue;
                    }else {
                        row.getCell(2).setCellType(CellType.STRING);
                        stuFor_xsl.setsNo(row.getCell(2).getStringCellValue());
                        System.out.println(row.getCell(2));
                    }
                    if (row.getCell(3).toString().equals("")) {
                        row.getCell(0).setCellType(CellType.STRING);
                        fail+=1;
                        ErrorsList.add("序号为"+row.getCell(0).getStringCellValue()+"的数据导入失败，院系不能为空");
                        continue;
                    }else if (studentsDao.getDeptByName(row.getCell(3).getStringCellValue()).size()!=0){
                        row.getCell(3).setCellType(CellType.STRING);
                        stuFor_xsl.setDeptId(studentsDao.getDeptByName(row.getCell(3).getStringCellValue()).get(0).getDeptId());
                        System.out.println(row.getCell(3));
                    }
                    else{
                        ErrorsList.add("序号为："+row.getCell(0).getStringCellValue()+"的信息导入失败，院系不存在");
                    }
                    if (row.getCell(4).toString().equals("")){
                        row.getCell(0).setCellType(CellType.STRING);
                        fail+=1;
                        ErrorsList.add("序号为"+row.getCell(0).getStringCellValue()+"的数据导入失败，专业不能为空");
                        continue;
                    }else if (studentsDao.getMajorByName(row.getCell(4).getStringCellValue()).size()!=0){
                        row.getCell(4).setCellType(CellType.STRING);
                        stuFor_xsl.setMajorCode(studentsDao.getMajorByName(row.getCell(4).getStringCellValue()).get(0).getMajorCode());
                        System.out.println(row.getCell(4));
                    }
                    if (row.getCell(5).toString().equals("")){
                        row.getCell(0).setCellType(CellType.STRING);
                        fail+=1;
                        ErrorsList.add("序号为"+row.getCell(0).getStringCellValue()+"的数据导入失败，班级不能为空");
                        continue;
                    }else if (studentsDao.getClassByName(row.getCell(5).getStringCellValue()).size()!=0){
                        row.getCell(5).setCellType(CellType.STRING);
                        stuFor_xsl.setClassCode(studentsDao.getClassByName(row.getCell(5).getStringCellValue()).get(0).getClassCode());
                        System.out.println(studentsDao.getClassByName(row.getCell(5).getStringCellValue()).get(0).getClassCode());
                    }else {
                        ErrorsList.add("序号为："+row.getCell(0).getStringCellValue()+"的信息导入失败，班级不存在");
                    }
                    if (row.getCell(6).toString().equals("")){
                        row.getCell(0).setCellType(CellType.STRING);
                        fail+=1;
                        ErrorsList.add("序号为"+row.getCell(0).getStringCellValue()+"的数据导入失败，姓名不能为空");
                        continue;
                    }else {
                        row.getCell(6).setCellType(CellType.STRING);
                        stuFor_xsl.setsName(row.getCell(6).getStringCellValue());
                        System.out.println(row.getCell(6));
                    }
                    if (row.getCell(7).toString().equals("")){
                        row.getCell(0).setCellType(CellType.STRING);
                        fail+=1;
                        ErrorsList.add("序号为"+row.getCell(0).getStringCellValue()+"的数据导入失败，性别不能为空");
                        continue;
                    }else if (row.getCell(7).toString().equals("男")||row.getCell(7).toString().equals("女")){
                        row.getCell(7).setCellType(CellType.STRING);
                        stuFor_xsl.setsGender(row.getCell(7).getStringCellValue());
                        System.out.println(row.getCell(7));
                    }else {
                        row.getCell(0).setCellType(CellType.STRING);
                        fail+=1;
                        ErrorsList.add("序号为"+row.getCell(0).getStringCellValue()+"的数据导入失败，性别取值只能为‘男’或‘女’");
                        continue;
                    }
                    if (row.getCell(8).toString().matches("^1[3|4|5|7|8][0-9]\\\\d{4,8}$")&&!row.getCell(8).toString().equals("")){
                        row.getCell(0).setCellType(CellType.STRING);
                        fail+=1;
                        ErrorsList.add("序号为"+row.getCell(0).getStringCellValue()+"的数据导入失败，请输入正确的手机号");
                        continue;
                    }else {
                        row.getCell(8).setCellType(CellType.STRING);
                        stuFor_xsl.setsTel(row.getCell(8).getStringCellValue());
                        System.out.println(row.getCell(8));
                    }
                    System.out.println(stuFor_xsl);//输出实体信息
                    if (stuFor_xsl.getDeptId()!=null&&stuFor_xsl.getsGrade()!=null
                            &&stuFor_xsl.getMajorCode()!=null&&stuFor_xsl.getClassCode()!=null
                            &&stuFor_xsl.getsNo()!=null&&stuFor_xsl.getsName()!=null
                            &&stuFor_xsl.getsGender()!=null&&stuFor_xsl.getsTel()!=null){
                        //将读取到的数据插入学生数据表
                        try {
                            if (studentsDao.insert(stuFor_xsl)!=0){
                                success+=1;
                            }
                        }catch (Exception e) {
                            ErrorsList.add("序号为" + row.getCell(0).getStringCellValue() + "的数据导入失败，请检查该学生信息是否已存在");
                            fail+=1;
                        }
                    }
                }
                System.out.println("end");
                result.put("code", 200);
                result.put("message", "导入完成！成功导入"+success+"条数据,"+fail+"条数据导入失败");
            }else {
                result.put("error","请使用格式正确的导入模板");
            }
        } catch (Exception e1) {
            // 回滚数据
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e1.printStackTrace();
        } finally {
            try {
                im.close();
                wb.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        result.put("errors",ErrorsList);
        return result;
    }

    @Override
    public List<StudentsEntity> getStuByOpenid(String openId) {
//        QueryWrapper<StudentsEntity> wrapper=new QueryWrapper<>();
//        wrapper.eq("s_openid",openId);

        return studentsDao.getStuByOpenid(openId);
    }

    @Override
    public List<DeptEntity> getDeptByName(String name) {
        return studentsDao.getDeptByName(name);
    }

    @Override
    public List<MajorEntity> getMajorByName(String name) {
        return studentsDao.getMajorByName(name);
    }

    @Override
    public List<ClassEntity> getClassByName(String name) {
        return studentsDao.getClassByName(name);
    }


    //判断行是否为空
    public static boolean isRowEmpty(Row row){
        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK){
                return false;
            }
        }
        return true;
    }
    }