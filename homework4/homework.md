# 《第四次作业:SQL 练习》

> **学院：省级示范性软件学院**
>
> **课程：javaweb**
>
> **题目：《第四次作业:SQL 练习》**
>
> **姓名：刘双硕**
>
> **学号：2100150421**
>
> **班级：软工2201**
>
> **日期：2024-10-20**
>
> **实验环境： MySQL8**

## 一、实验目的

通过大量的SQL练习，提高SQL技能.

## 二、实验内容

### **员工信息练习题**

#### 1. 查询所有员工的姓名、邮箱和工作岗位。

```mysql
select
    CONCAT(first_name,last_name) as name,
    email,
    job_title
from employees;
```

#### 2. 查询所有部门的名称和位置。

```mysql
select
    dept_name,
    location
from departments;
```

#### 3. 查询工资超过70000的员工姓名和工资。

```mysql
select
    CONCAT(first_name,last_name) as name,
    salary
from employees
where salary>70000;
```

#### 4. 查询IT部门的所有员工。

```mysql
select CONCAT(first_name,last_name) as name
from employees
natural join departments
where dept_name= 'IT';
```

#### 5. 查询入职日期在2020年之后的员工信息。 

```mysql
select *
from employees
where hire_date>='2020-01-01';
```

#### 6. 计算每个部门的平均工资。

```mysql
select
    dept_name,
    avg(salary) as avg_salary
from departments
natural join employees
group by dept_name;
```

#### 7. 查询工资最高的前3名员工信息。

```mysql
select *
from employees
order by salary desc
limit 3;
```

#### 8. 查询每个部门员工数量。

```mysql
select
    dept_name,
    count(*)
from departments
natural join employees
group by dept_name;
```

#### 9. 查询没有分配部门的员工

```mysql
select
    *
from employees
where dept_id IS NULL;
```

#### 10. 查询参与项目数量最多的员工。

```mysql
select
    emp_id
from employee_projects as ep
group by emp_id
having count(*)=(
    select
        count(*)
    from employee_projects as ep
    group by emp_id
    order by count(*) desc
    limit 1
);
```

#### 11. 计算所有员工的工资总和。

```mysql
select
    sum(salary) as sum_salary
from employees;
```

#### 12. 查询姓"Smith"的员工信息。

```mysql
select
    *
from employees
where last_name='Smith';
```

#### 13. 查询即将在半年内到期的项目。

```mysql
select
    project_name
from projects
where end_date <= date_add(curdate(), interval 6 month);
```

#### 14. 查询至少参与了两个项目的员工。

```mysql
select
    emp_id
from employee_projects as ep
natural join employees
group by emp_id
having count(*)>=2;
```

#### 15. 查询没有参与任何项目的员工。

```mysql
select
    emp_id
from employees
where emp_id not in(
    select emp_id
    from employee_projects
);
```

#### 16. 计算每个项目参与的员工数量。

```mysql
select
    project_id,
    count(*) as emp_num
from employee_projects
group by project_id;
```

#### 17. 查询工资第二高的员工信息。

```mysql
select
    *
from (
    select
        *,
        rank() over ( order by salary desc) as rk
    from employees
)a
where rk=2;
```

#### 18. 查询每个部门工资最高的员工。

```mysql
select
    dept_name,
    CONCAT(first_name,last_name) as name
from (
    select
        first_name,
        last_name,
        dept_id,
        rank() over ( partition by dept_id order by salary desc) as rk
    from employees
)a
natural join departments
where rk=1;
```

#### 19. 计算每个部门的工资总和,并按照工资总和降序排列。

```mysql
select
    dept_name,
    sum(salary) as sum_salary
from departments as dp
natural join employees
group by dp.dept_id
order by sum_salary desc;
```

#### 20. 查询员工姓名、部门名称和工资。

```mysql
select
    CONCAT(first_name,last_name) as name,
    dept_name,
    salary
from employees
natural join departments;
```

#### 21. 查询每个员工的上级主管(假设emp_id小的是上级)

```mysql
select
    CONCAT(first_name,last_name) as name,
    a.name as manager_name
from employees e
left join (
    select
        emp_id,
        dept_id,
        CONCAT(first_name,last_name) as name
    from employees
)a on a.emp_id<e.emp_id and e.dept_id=a.dept_id;
```

#### 22. 查询所有员工的工作岗位,不要重复。

```mysql
select distinct
    CONCAT(first_name,last_name) as name,
    job_title
from employees;
```

#### 23. 查询平均工资最高的部门。

```mysql
select
    dept_name
from (
    select
        dept_id,
        rank() over (order by avg(salary) desc ) as rk
    from departments
    natural join employees
    group by dept_id
)a
natural join departments
where rk=1;
```

#### 24. 查询工资高于其所在部门平均工资的员工。

```mysql
select
    CONCAT(first_name,last_name) as name
from employees
natural join (
    select
        dept_id,
        avg(salary) as avg_salary
    from employees
    group by dept_id
)a
where salary>avg_salary;
```

#### 25. 查询每个部门工资前两名的员工。

```mysql
select
    dept_name,
    CONCAT(first_name,last_name) as name
from (
    select
        first_name,
        last_name,
        dept_id,
        rank() over ( partition by dept_id order by salary desc) as rk
    from employees
)a
natural join departments
where rk<=2;
```

## **学生选课题**

#### 1. 查询所有学生的信息。

```mysql
select
    *
from student;
```

#### 2. 查询所有课程的信息。

```mysql
select
    *
from course;
```

#### 3. 查询所有学生的姓名、学号和班级。

```mysql
select
    name,
    student_id,
    my_class
from student;
```

#### 4. 查询所有教师的姓名和职称。

```mysql
select
    name,
    title
from teacher;
```

#### 5. 查询不同课程的平均分数。

```mysql
select
    course_id,
    avg(score)
from score
group by course_id;
```

#### 6. 查询每个学生的平均分数。

```mysql
select
    student_id,
    avg(score)
from student
natural join score
group by student_id;
```

#### 7. 查询分数大于85分的学生学号和课程号。

```mysql
select
    student_id,
    course_id
from score
where score>85;
```

#### 8. 查询每门课程的选课人数。

```mysql
select
    course_id,
    count(*) as chosse_num
from score
group by course_id;
```

#### 9. 查询选修了"高等数学"课程的学生姓名和分数。

```mysql
select
    name,
    score
from score
natural join course
natural join student
where  course_name='高等数学';
```

#### 10. 查询没有选修"大学物理"课程的学生姓名。

```mysql
select student_id
from student
where student_id not in(
    select
        student_id
    from score
    natural join course
    where course_name='大学物理'
);
```

#### 11. 查询C001比C002课程成绩高的学生信息及课程分数。

```mysql
select
    student.*,
    course_id,
    score
from score
natural join student
where student_id in(
    select
        s1.student_id
    from score as s1
    join score as s2 on s1.student_id=s2.student_id
    where s1.course_id='C001' AND s2.course_id='C002'  AND s1.score>s2.score
) AND (course_id='C001' or course_id='C002' );
```

#### 12. 统计各科成绩各分数段人数：课程编号，课程名称，[100-85]，[85-70]，[70-60]，[60-0] 及所占百分比

```mysql
select
    course_id,
    course_name,
    sum(if(score between 85 and 100,1,0)) as '100-85人数',
    CONCAT(ROUND(sum(if(score between 85 and 100,1,0))/count(*) * 100, 2), '%') as '100-85占比',
    sum(if(score between 75 and 85,1,0)) as '85-70人数',
    CONCAT(ROUND(sum(if(score between 75 and 85,1,0))/count(*) * 100, 2), '%') as '85-70占比',
    sum(if(score between 60 and 70,1,0)) as '70-60人数',
    CONCAT(ROUND(sum(if(score between 60 and 70,1,0))/count(*) * 100, 2), '%') as '70-60占比',
    sum(if(score between 0 and 60,1,0)) as '60-0人数',
    CONCAT(ROUND(sum(if(score between 0 and 60,1,0))/count(*) * 100, 2), '%') as '60-0占比'
from score
natural join course
group by course_id,course_name;
```

#### 13. 查询选择C002课程但没选择C004课程的成绩情况(不存在时显示为 null )。

```mysql
select
    s.student_id,
    name,
    course_id,
    score
from score as s
natural join student
where course_id='C002' AND s.student_id not in(
    select
        student_id
    from score
    where course_id='C004'
);
```

#### 14. 查询平均分数最高的学生姓名和平均分数。

```mysql
select
    name,
    avg_score
from student
join (
    select
        student_id,
        avg(score) as avg_score
    from student
    natural join score
    group by student_id
    order by avg_score desc
    limit 1
)a on student.student_id=a.student_id;
```

#### 15. 查询总分最高的前三名学生的姓名和总分。

```mysql
select
    name,
    sum_score
from student
join (
    select
        student_id,
        sum(score) as sum_score
    from student
    natural join score
    group by student_id
    order by sum_score desc
    limit 3
)a on student.student_id=a.student_id;
```

####  16. 查询各科成绩最高分、最低分和平均分。要求如下：以如下形式显示：课程 ID，课程 name，最高分，最低分，平均分，及格率，中等率，优良率，优秀率及格为>=60，中等为：70-80，优良为：80-90，优秀为：>=90要求输出课程号和选修人数，查询结果按人数降序排列，若人数相同，按课程号升序排列

```mysql
select
    course_id,
    course_name,
    count(*) as '选修人数',
    max(score) as '最高分',
    min(score) as '最低分',
    avg(score) as '平均分',
    CONCAT(ROUND(sum(if(score >=60,1,0))/count(*) * 100, 2), '%') as '及格率',
    CONCAT(ROUND(sum(if(score between 70 and 80,1,0))/count(*) * 100, 2), '%') as '中等率',
    CONCAT(ROUND(sum(if(score between 80 and 90,1,0))/count(*) * 100, 2), '%') as '优良率',
    CONCAT(ROUND(sum(if(score >=90,1,0))/count(*) * 100, 2), '%') as '优秀率'
from score
natural join course
group by course_id,course_name;
```

#### 17. 查询男生和女生的人数。

```mysql
select
    sum(if(gender='男',1,0)) as '男生人数',
    sum(if(gender='女',1,0)) as '女生人数'
from student;
```

#### 18. 查询年龄最大的学生姓名。

```mysql
select
    name
from student
order by birth_date
limit 1;
```

#### 19. 查询年龄最小的教师姓名。

```mysql
select
    name
from teacher
order by birth_date desc
limit 1;
```

#### 20. 查询学过「张教授」授课的同学的信息。

```mysql
select
    student.*
from teacher
join course on teacher.teacher_id = course.teacher_id
join score on course.course_id = score.course_id
join student on score.student_id = student.student_id
where teacher.name='张教授';
```

#### 21. 查询查询至少有一门课与学号为"2021001"的同学所学相同的同学的信息 。

```mysql
select
  student.*
from student
natural join (
    select
        distinct student_id
    from score
    where course_id in(
        select
            course_id
        from score
        where student_id='2021001'
    )
)a;
```

#### 22. 查询每门课程的平均分数，并按平均分数降序排列。

```mysql
select
    course_id,
    avg(score) as avg_score
from score
group by course_id
order by avg_score desc ;
```

#### 23. 查询学号为"2021001"的学生所有课程的分数。

```mysql
select
    course_id,
    score
from score
where student_id='2021001';
```

#### 24. 查询所有学生的姓名、选修的课程名称和分数。

```mysql
select
    name,
    course_name,
    score
from score
natural join student
natural join course;
```

#### 25. 查询每个教师所教授课程的平均分数。

```mysql
select
    teacher_id,
    course_id,
    avg(score) as avg_score
from teacher
natural join course
natural join score
group by teacher_id,course_id;
```

#### 26. 查询分数在80到90之间的学生姓名和课程名称。

```mysql
select
    name,
    course_name
from score
natural join course
natural join student
where score between 80 and 90;
```

#### 27. 查询每个班级的平均分数。

```mysql
select
    my_class,
    avg(score) as avg_score
from student
natural join score
group by my_class;
```

#### 28. 查询没学过"王讲师"老师讲授的任一门课程的学生姓名。

```mysql
select
    distinct name
from score
natural join student
where student_id not in(
    select
        student_id
    from course
    join teacher on course.teacher_id = teacher.teacher_id
    join score on course.course_id = score.course_id
    where teacher.name='王讲师'
);
```

#### 29. 查询两门及其以上小于85分的同学的学号，姓名及其平均成绩 。

```mysql
select
    student_id,
    name,
    avg(score) as avg_score
from score
natural join student
group by student_id
having sum(if(score<85,1,0)) >=2;
```

#### 30. 查询所有学生的总分并按降序排列。

```mysql
select
    student_id,
    sum(score) as sum_score
from score
group by student_id
order by sum_score desc;
```

#### 31. 查询平均分数超过85分的课程名称。

```mysql
select
    course_name
from score
natural join course
group by course_id
having avg(score) > 85;
```

#### 32. 查询每个学生的平均成绩排名。

```mysql
select
    name,
    rank() over (order by avg_score) as rk
from(
    select distinct
    name,
    avg(score) over(partition by student_id) as avg_score
from score
natural join student
)as a;
```

#### 33. 查询每门课程分数最高的学生姓名和分数。

```mysql
select
    max(score) as m
from score
group by course_id;
```

#### 34. 查询选修了"高等数学"和"大学物理"的学生姓名。

```mysql
select
    name
from student
natural join (
    select
        student_id
    from score
    natural join course
    group by student_id
    having sum(if(course_name='高等数学' or course_name='大学物理',1,0))=2
)a;
```

#### 35. 按平均成绩从高到低显示所有学生的所有课程的成绩以及平均成绩（没有选课则为空）。

```mysql
select
    student_id,
    avg(score) as avg_score
from score
natural join student
group by student_id
order by avg_score desc ;
```

#### 36. 查询分数最高和最低的学生姓名及其分数。

```mysql
with max as (
    select student_id,score
    from score
    order by score desc
    limit 1
),
min as (
    select student_id,score
    from score
    order by score
    limit 1
)
select
    max.student_id as max_id,
    max.score as max_score,
    min.student_id as min_id,
    min.score as min_score
from max,min;
```

#### 37. 查询每个班级的最高分和最低分。

```mysql
select
    my_class,
    max(score) as max_score,
    min(score) as min_score
from score
natural join student
group by my_class;
```

#### 38. 查询每门课程的优秀率（优秀为90分）。

```mysql
select
    course_id,
    course_name,
    CONCAT(ROUND(sum(if(score >=90,1,0))/count(*) * 100, 2), '%') as '优秀率'
from score
natural join course
group by course_id,course_name;
```

#### 39. 查询平均分数超过班级平均分数的学生。

```mysql
select
    distinct student_id
from(
    select
        student_id,
        avg(score) over (partition by student_id) as student_avg,
        avg(score) over (partition by my_class) as class_avg
    from score
    natural join student
)a
where student_avg>class_avg;
```

#### 40. 查询每个学生的分数及其与课程平均分的差值。

```mysql
select
    student_id,
    course_id,
    score - course_avg as difference
from(
    select
        student_id,
        course_id,
        score,
        avg(score) over (partition by course_id) as course_avg
    from score
)a;
```

## 三、问题及解决办法

- 查询排名的题目使用一般方法不好解决
  - 进行子查询,在里层使用rank窗口函数,得到排名,在外面对排名进行where过滤.
