# Javavm

一个用 Java 实现的玩具 JVM（Toy JVM），支持类文件解析、类加载和字节码解释执行。

## 项目概述

Javavm 是一个学习性质的 Java 虚拟机实现，旨在帮助理解 JVM 的内部工作原理。项目采用 Java 8 开发，使用 Maven 进行构建管理。

### 特性

- 完整的类文件（.class）解析
- 类加载机制（启动类、扩展类、用户类）
- 运行时数据区实现（栈帧、操作数栈、局部变量表）
- JVM 字节码指令集实现（常量、加载、存储、运算、比较、转换、控制流、引用等）
- 运行时常量池
- 方法区堆内存管理

## 项目架构

### 整体架构图

```
┌─────────────────────────────────────────────────────────────┐
│                        Bootstrap                         │
│                    (程序入口、命令行)                      │
└──────────────────┬──────────────────────────────────────────┘
                   │
                   ▼
┌─────────────────────────────────────────────────────────────┐
│                        Classpath                         │
│              (启动类路径、扩展类路径、用户类路径)            │
└──────────────────┬──────────────────────────────────────────┘
                   │
                   ▼
┌─────────────────────────────────────────────────────────────┐
│                       ClassLoader                       │
│              (类加载、解析、验证、准备)                     │
└──────────────────┬──────────────────────────────────────────┘
                   │
                   ▼
┌─────────────────────────────────────────────────────────────┐
│                    Interpreter                         │
│                    (指令解释执行)                          │
└──────────────────┬──────────────────────────────────────────┘
                   │
                   ▼
┌─────────────────────────────────────────────────────────────┐
│              Runtime Data Areas                          │
│    (JvmThread → JvmStack → Frame → LocalVars/OperandStack)  │
└─────────────────────────────────────────────────────────────┘
```

### 核心模块

#### 1. cn.abelib.javavm - 启动和配置

**核心类：**

| 类名 | 功能 |
|------|------|
| `Bootstrap` | 主程序入口，负责 JVM 启动和初始化 |
| `Command` | 命令行参数解析，使用 Apache Commons CLI |
| `Classpath` | 类路径管理，管理启动类、扩展类和用户类路径 |
| `Constants` | JVM 常量定义（访问标志、类型等） |
| `ClassReader` | 类文件字节读取器 |

**启动流程：**

```java
// 1. 解析命令行参数
Command command = new Command();
command.parseCmd(args);

// 2. 初始化类路径
Classpath classpath = new Classpath();
classpath.parse(cmd.getXJreOption(), cmd.getCpOption());

// 3. 创建类加载器
ClassLoader classLoader = new ClassLoader(classpath, cmd.isVerboseClassFlag());

// 4. 加载主类
Clazz clazz = classLoader.loadClass(className);

// 5. 查找并执行 main 方法
Method mainMethod = clazz.getMainMethod();
Interpreter.interpret(mainMethod, cmd.isVerboseInstFlag());
```

#### 2. cn.abelib.javavm.clazz - 类文件结构解析

**核心类：**

| 类名 | 功能 |
|------|------|
| `ClassFile` | 类文件主类，解析 .class 文件结构 |
| `MemberInfo` | 成员信息（字段和方法的通用表示） |
| `ClassReader` | 字节码读取器 |

**子模块：**

- **constantinfo** - 常量池各种常量类型
  - `ConstantInfo` - 常量基类
  - `ConstantUtf8Info` - UTF-8 字符串常量
  - `ConstantIntegerInfo` - 整数常量
  - `ConstantFloatInfo` - 浮点数常量
  - `ConstantLongInfo` - 长整型常量
  - `ConstantDoubleInfo` - 双精度浮点常量
  - `ConstantClassInfo` - 类引用
  - `ConstantStringInfo` - 字符串常量
  - `ConstantFieldRefInfo` - 字段引用
  - `ConstantMethodRefInfo` - 方法引用
  - `ConstantInterfaceMethodRefInfo` - 接口方法引用
  - `ConstantNameAndTypeInfo` - 名称和类型描述符
  - `ConstantMethodHandleInfo` - 方法句柄
  - `ConstantMethodTypeInfo` - 方法类型
  - `ConstantInvokeDynamicInfo` - 动态调用点
  - `ConstantModuleInfo` - 模块信息
  - `ConstantPackageInfo` - 包信息
  - `ConstantDynamicInfo` - 动态常量

- **attributeinfo** - 属性信息
  - `AttributeInfo` - 属性基类
  - `AttributeInfos` - 属性信息工厂
  - `CodeAttribute` - 代码属性，包含字节码
  - `ConstantValueAttribute` - 常量值属性
  - `ExceptionsAttribute` - 异常表属性
  - `SourceFileAttribute` - 源文件属性
  - `LineNumberTableAttribute` - 行号表属性
  - `LocalVariableTableAttribute` - 局部变量表属性
  - `DeprecatedAttribute` - 废弃属性
  - `SyntheticAttribute` - 合成属性
  - `MarkerAttribute` - 标记属性
  - `UnparsedAttribute` - 未解析属性

**ClassFile 结构：**

```java
public class ClassFile {
    private long magic;              // 0xCAFEBABE
    private int minorVersion;
    private int majorVersion;
    private ConstantPool constantPool;
    private int accessFlags;
    private int thisClass;
    private int superClass;
    private int[] interfaces;
    private MemberInfo[] fields;
    private MemberInfo[] methods;
    private AttributeInfo[] attributes;
}
```

#### 3. cn.abelib.javavm.runtime - 运行时数据区

**核心类：**

| 类名 | 功能 |
|------|------|
| `JvmThread` | JVM 线程，包含程序计数器和虚拟机栈 |
| `JvmStack` | JVM 虚拟机栈（固定大小 1024） |
| `Frame` | 栈帧，包含局部变量表和操作数栈 |
| `LocalVars` | 局部变量表 |
| `OperandStack` | 操作数栈 |
| `Slot` | 数据槽，可存储数值或对象引用 |

**运行时数据区结构：**

```
JvmThread
├── pc (程序计数器)
└── stack (JVM 虚拟机栈)
    └── Frame (栈帧)
        ├── localVars (局部变量表)
        │   └── Slot[] (数据槽数组)
        ├── operandStack (操作数栈)
        │   └── Slot[] (数据槽数组)
        ├── thread (所属线程)
        ├── method (关联的方法)
        └── nextPC (下一条指令的 PC 值)
```

**Slot 数据结构：**

```java
public class Slot {
    private int num;        // 存储数值类型（int, float 的 bit 表示）
    private JvmObject ref;  // 存储对象引用
}
```

**长整型/双精度浮点数占用两个连续的槽位。**

#### 4. cn.abelib.javavm.runtime.heap - 堆内存管理

**核心类：**

| 类名 | 功能 |
|------|------|
| `Clazz` | 运行时类表示 |
| `Method` | 方法 |
| `Field` | 字段 |
| `ClassLoader` | 类加载器 |
| `RuntimeConstantPool` | 运行时常量池 |
| `RuntimeConstantPoolInfo` | 运行时常量池项 |
| `JvmObject` | JVM 对象 |
| `StringPool` | 字符串池 |
| `MethodRef` | 方法符号引用 |
| `FieldRef` | 字段符号引用 |
| `InterfaceMethodRef` | 接口方法符号引用 |
| `MemberRef` | 成员符号引用基类 |
| `SymRef` | 符号引用基类 |
| `AccessFlags` | 访问标志 |

**类加载过程：**

```java
// 1. 加载阶段
loadClass(name)
    ↓
loadNonArrayClass(name) // 加载非数组类
    ↓
readClass(name)        // 从 classpath 读取字节码
    ↓
defineClass(data)      // 定义类

// 2. 链接阶段
link(clazz)
    ↓
verify(clazz)         // 验证（未实现）
    ↓
prepare(clazz)        // 准备
    ├── calcInstanceFieldSlotIds()  // 计算实例字段槽位 ID
    ├── calcStaticFieldSlotIds()    // 计算静态字段槽位 ID
    └── allocAndInitStaticVars()   // 分配并初始化静态变量
```

**运行时常量池：**

运行时常量池将类文件中的常量池转换为运行时可直接使用的形式：

```java
public class RuntimeConstantPool {
    private Clazz clazz;
    private RuntimeConstantPoolInfo[] consts;
}
```

#### 5. cn.abelib.javavm.instructions - 指令集实现

项目实现了大部分 JVM 字节码指令，按功能分类如下：

##### 5.1 base - 指令基础

| 类名 | 功能 |
|------|------|
| `Instruction` | 指令接口，定义 `fetchOperands` 和 `execute` |
| `Instructions` | 指令工厂，根据 opcode 创建指令实例 |
| `Interpreter` | 字节码解释器，循环解码执行指令 |
| `BytecodeReader` | 字节码读取器 |
| `BranchInstruction` | 分支指令基类 |
| `Index16Instruction` | 16 位索引指令基类 |
| `Index8Instruction` | 8 位索引指令基类 |
| `NoOperandsInstruction` | 无操作数指令基类 |
| `ArrayInstruction` | 数组指令基类 |
| `InitClazz` | 类初始化指令 |
| `IntegerReturn` | int 返回指令 |
| `LongReturn` | long 返回指令 |
| `FloatReturn` | float 返回指令 |
| `DoubleReturn` | double 返回指令 |
| `RefReturn` | 引用返回指令 |
| `Return` | void 返回指令 |

**解释执行流程：**

```java
loop(JvmThread thread, boolean printLog) {
    while (true) {
        Frame frame = thread.popFrame();
        int pc = frame.getNextPc();
        thread.setPc(pc);

        // 解码
        reader.reset(frame.getMethod().getCode(), pc);
        int opcode = reader.readUInt8();
        Instruction inst = Instructions.newInstruction(opcode);
        inst.fetchOperands(reader);  // 读取操作数
        frame.setNextPC(reader.getPc());

        // 执行
        inst.execute(frame);
    }
}
```

##### 5.2 constants - 常量指令

| 指令 | 功能 | opcode |
|------|------|--------|
| `Nop` | 空操作 | 0x00 |
| `AConstNull` | 将 null 推入栈 | 0x01 |
| `IConstM1` | 将 -1 推入栈 | 0x02 |
| `IConst0` ~ `IConst5` | 将 0~5 推入栈 | 0x03~0x08 |
| `LConst0` ~ `LConst1` | 将 0/1L 推入栈 | 0x09~0x0a |
| `FConst0` ~ `FConst2` | 将 0.0/1.0/2.0f 推入栈 | 0x0b~0x0d |
| `DConst0` ~ `DConst1` | 将 0.0/1.0d 推入栈 | 0x0e~0x0f |
| `BIPush` | 将 byte 推入栈 | 0x10 |
| `SIPush` | 将 short 推入栈 | 0x11 |
| `LoadConstant` (ldc) | 从常量池加载值推入栈 | 0x12 |
| `LoadConstantWide` (ldc_w) | 从常量池加载值（宽索引） | 0x13 |
| `LoadConstant2Wide` (ldc2_w) | 从常量池加载 long/double | 0x14 |

##### 5.3 loads - 加载指令

| 指令 | 功能 | opcode |
|------|------|--------|
| `ILoad` / `ILoad0`~`ILoad3` | 从局部变量加载 int | 0x15 / 0x1a~0x1d |
| `LLoad` / `LLOAD0`~`LLOAD3` | 从局部变量加载 long | 0x16 / 0x1e~0x21 |
| `FLoad` / `FLOAD0`~`FLOAD3` | 从局部变量加载 float | 0x17 / 0x22~0x25 |
| `DLoad` / `DLOAD0`~`DLOAD3` | 从局部变量加载 double | 0x18 / 0x26~0x29 |
| `ALoad` / `ALOAD0`~`ALOAD3` | 从局部变量加载引用 | 0x19 / 0x2a~0x2d |

##### 5.4 stores - 存储指令

| 指令 | 功能 | opcode |
|------|------|--------|
| `IStore` / `IStore0`~`IStore3` | 将 int 存入局部变量 | 0x36 / 0x3b~0x3e |
| `LStore` / `LStore0`~`LStore3` | 将 long 存入局部变量 | 0x37 / 0x3f~0x42 |
| `FStore` / `FStore0`~`FStore3` | 将 float 存入局部变量 | 0x38 / 0x43~0x46 |
| `DStore` / `DStore0`~`DStore3` | 将 double 存入局部变量 | 0x39 / 0x47~0x4a |
| `AStore` / `AStore0`~`AStore3` | 将引用存入局部变量 | 0x3a / 0x4b~0x4e |

##### 5.5 maths - 数学运算指令

| 指令 | 功能 | opcode |
|------|------|--------|
| `IAdd` / `LAdd` / `FAdd` / `DAdd` | 加法 | 0x60~0x63 |
| `ISub` / `LSub` / `FSub` / `DSub` | 减法 | 0x64~0x67 |
| `IMul` / `LMul` / `FMul` / `DMul` | 乘法 | 0x68~0x6b |
| `IDiv` / `LDiv` / `FDiv` / `DDiv` | 除法 | 0x6c~0x6f |
| `IRem` / `LRem` / `FRem` / `DRem` | 取模 | 0x70~0x73 |
| `INeg` / `LNeg` / `FNeg` / `DNeg` | 取反 | 0x74~0x77 |
| `IShl` / `LShl` | 左移 | 0x78 / 0x79 |
| `IShr` / `LShr` | 算术右移 | 0x7a / 0x7b |
| `IUShr` / `LUShr` | 逻辑右移 | 0x7c / 0x7d |
| `IAnd` / `LAnd` | 按位与 | 0x7e / 0x7f |
| `IOr` / `LOr` | 按位或 | 0x80 / 0x81 |
| `IXor` / `LXor` | 按位异或 | 0x82 / 0x83 |
| `IInc` | 局部变量自增 | 0x84 |

##### 5.6 comparisons - 比较指令

| 指令 | 功能 | opcode |
|------|------|--------|
| `LCMP` | long 比较 | 0x94 |
| `FCMPL` / `FCMPG` | float 比较 | 0x95 / 0x96 |
| `DCMPL` / `DCMPG` | double 比较 | 0x97 / 0x98 |

##### 5.7 conversions - 类型转换指令

| 指令 | 功能 | opcode |
|------|------|--------|
| `I2L` / `I2F` / `I2D` | int 转 long/float/double | 0x85~0x87 |
| `L2I` / `L2F` / `L2D` | long 转 int/float/double | 0x88~0x8a |
| `F2I` / `F2L` / `F2D` | float 转 int/long/double | 0x8b~0x8d |
| `D2I` / `D2L` / `D2F` | double 转 int/long/float | 0x8e~0x90 |
| `I2B` / `I2C` / `I2S` | int 转 byte/char/short | 0x91~0x93 |

##### 5.8 controls - 控制流指令

| 指令 | 功能 | opcode |
|------|------|--------|
| `IfEqual` | 引用相等则跳转 | 0x99 |
| `IfNotEqual` | 引用不等则跳转 | 0x9a |
| `IfLowThan` | int 小于则跳转 | 0x9b |
| `IfGreatEqualThan` | int 大于等于则跳转 | 0x9c |
| `IfGreatThan` | int 大于则跳转 | 0x9d |
| `IfLowEqualThan` | int 小于等于则跳转 | 0x9e |
| `IfIntegerCompareEqual` | int 相等则跳转 | 0x9f |
| `IfIntegerCompareNotEqual` | int 不等则跳转 | 0xa0 |
| `IfIntegerCompareLowThan` | int 小于则跳转 | 0xa1 |
| `IfIntegerCompareGreatEqual` | int 大于等于则跳转 | 0xa2 |
| `IfIntegerCompareGreatThan` | int 大于则跳转 | 0xa3 |
| `IfIntegerCompareLowEqual` | int 小于等于则跳转 | 0xa4 |
| `IfRefCompareEqual` | 引用相等则跳转 | 0xa5 |
| `IfRefCompareNotEqual` | 引用不等则跳转 | 0xa6 |
| `Goto` | 无条件跳转 | 0xa7 |
| `TableSwitch` | 表跳转 | 0xaa |
| `LookupSwitch` | 查找表跳转 | 0xab |
| `IfNull` | 为 null 则跳转 | 0xc6 |
| `IfNonNull` | 不为 null 则跳转 | 0xc7 |
| `GotoWide` | 宽跳转 | 0xc8 |

##### 5.9 references - 引用指令

| 指令 | 功能 | opcode |
|------|------|--------|
| `GetStatic` | 获取静态字段 | 0xb2 |
| `PutStatic` | 设置静态字段 | 0xb3 |
| `GetField` | 获取实例字段 | 0xb4 |
| `PutField` | 设置实例字段 | 0xb5 |
| `InvokeVirtual` | 调用实例方法 | 0xb6 |
| `InvokeSpecial` | 调用构造/私有/父类方法 | 0xb7 |
| `InvokeStatic` | 调用静态方法 | 0xb8 |
| `InvokeInterface` | 调用接口方法 | 0xb9 |
| `New` | 创建对象实例 | 0xbb |
| `NewArray` | 创建基本类型数组 | 0xbc |
| `RefNewArray` (anewarray) | 创建引用类型数组 | 0xbd |
| `ArrayLength` | 获取数组长度 | 0xbe |
| `RefThrow` (athrow) | 抛出异常 | 0xbf |
| `CheckCast` | 类型检查转换 | 0xc0 |
| `InstanceOf` | 类型判断 | 0xc1 |
| `MultiArrayNewArray` | 创建多维数组 | 0xc5 |

##### 5.10 stacks - 栈操作指令

| 指令 | 功能 | opcode |
|------|------|--------|
| `Pop` | 弹出栈顶一个值 | 0x57 |
| `Pop2` | 弹出栈顶两个值或一个 double/long | 0x58 |
| `Duplicate` | 复制栈顶值 | 0x59 |
| `DuplicateX1` | 复制栈顶值到下方第三个位置 | 0x5a |
| `DuplicateX2` | 复制栈顶值到下方第四个位置 | 0x5b |
| `Duplicate2` | 复制栈顶两个值 | 0x5c |
| `Duplicate2X1` | 复制栈顶两个值到下方第四个位置 | 0x5d |
| `Duplicate2X2` | 复制栈顶两个值到下方第五个位置 | 0x5e |
| `Swap` | 交换栈顶两个值 | 0x5f |

##### 5.11 extended - 扩展指令

| 指令 | 功能 | opcode |
|------|------|--------|
| `Wide` | 扩展局部变量访问 | 0xc4 |

##### 5.12 return - 返回指令

| 指令 | 功能 | opcode |
|------|------|--------|
| `IntegerReturn` | 返回 int | 0xac |
| `LongReturn` | 返回 long | 0xad |
| `FloatReturn` | 返回 float | 0xae |
| `DoubleReturn` | 返回 double | 0xaf |
| `RefReturn` | 返回引用 | 0xb0 |
| `Return` | void 返回 | 0xb1 |

#### 6. cn.abelib.javavm.entry - 类路径管理

**核心类：**

| 类名 | 功能 |
|------|------|
| `Entry` | 类路径条目抽象类 |
| `DirEntry` | 目录类型类路径 |
| `ZipEntry` | Zip/Jar 文件类路径 |
| `WildcardEntry` | 通配符类路径（如 `lib/*`） |
| `CompositeEntry` | 复合类路径（多个路径用分隔符连接） |

**类路径查找顺序：**

1. 启动类路径（`jre/lib/*`）
2. 扩展类路径（`jre/lib/ext/*`）
3. 用户类路径（通过 `-cp` 或 `-classpath` 指定）

#### 7. cn.abelib.javavm.natives - Native 方法实现

**核心类：**

| 类名 | 功能 |
|------|------|
| `NativeRegistry` | Native 方法注册表 |
| `NativeMethod` | Native 方法接口 |

**已实现的 Native 方法：**

| 类 | 方法 | 功能 |
|----|------|------|
| `java/lang/Object` | getClass | 获取对象的 Class 对象 |
| `java/lang/Object` | hashCode | 获取哈希码 |
| `java/lang/Object` | clone | 对象克隆 |
| `java/lang/Class` | getName0 | 获取类名 |
| `java/lang/Class` | getPrimitiveClass | 获取基本类型的 Class |
| `java/lang/Class` | initClassName | 初始化类名 |
| `java/lang/Class` | desiredAssertionStatus0 | 断言状态 |
| `java/lang/Class` | setOut0 | 设置标准输出 |
| `java/lang/System` | arraycopy | 数组复制 |
| `java/lang/Float` | floatToRawIntBits | float 转 int 位模式 |
| `java/lang/Throwable` | fillInStackTrace | 填充异常栈 |
| `java/io/FileOutputStream` | writeBytes | 写入字节 |
| `sun/misc/VM` | initialize | VM 初始化 |

## 快速开始

### 编译项目

```bash
mvn clean compile
```

### 运行示例

```bash
# 编译测试类
cd src/test/java/cn/abelib/javavm
javac Hello.java

# 使用 Javavm 运行
cd /Users/abel/IdeaProjects/Javavm
java -cp target/classes cn.abelib.javavm.Bootstrap -verbose:inst cn.abelib.javavm.Hello
```

### 命令行参数

```
用法: java cn.abelib.javavm.Bootstrap [选项] <主类> [参数...]
选项:
  -?,-help                    打印帮助信息
  -version                    打印版本信息
  -verbose                    启用详细输出
  -verbose:class              启用类加载详细输出
  -verbose:inst              启用指令执行详细输出
  -cp,-classpath <路径>       指定类路径
  -Xjre <路径>               指定 JRE 路径
  -t                         显示当前时间
```

## 技术栈

- **语言**: Java 8
- **构建工具**: Maven
- **依赖库**:
  - JUnit 4.12 - 单元测试
  - Apache Commons CLI 1.4 - 命令行解析
  - Apache Commons Lang 3.3.2 - 工具类
  - Apache Commons IO 1.3.2 - IO 工具
  - Apache Commons Collections4 4.4 - 集合工具
  - Google Guava 20.0 - 核心库

## 项目限制

作为学习性质的玩具 JVM，存在以下限制：

1. **验证阶段未完成**：类文件验证（`verify()`）未实现
2. **异常处理不完善**：异常抛出已实现，但捕获机制不完整
3. **多线程不支持**：仅支持单线程执行
4. **GC 机制缺失**：未实现垃圾回收
5. **JIT 编译不支持**：仅支持解释执行
6. **部分功能待实现**：
   - 监视器进入/退出（monitorenter/monitorexit，同步）
   - 动态调用（invokedynamic）
   - JSR/RET 指令（已废弃）

## 目录结构

```
Javavm/
├── src/
│   ├── main/java/cn/abelib/javavm/
│   │   ├── clazz/              # 类文件解析
│   │   │   ├── attributeinfo/  # 属性信息
│   │   │   └── constantinfo/   # 常量池类型
│   │   ├── entry/              # 类路径管理
│   │   ├── instructions/       # 指令集实现
│   │   │   ├── base/           # 指令基础类
│   │   │   ├── constants/      # 常量指令
│   │   │   ├── loads/          # 加载指令
│   │   │   ├── stores/         # 存储指令
│   │   │   ├── maths/          # 数学运算指令
│   │   │   ├── comparisons/    # 比较指令
│   │   │   ├── conversions/    # 类型转换指令
│   │   │   ├── controls/       # 控制流指令
│   │   │   ├── references/     # 引用指令
│   │   │   ├── stacks/         # 栈操作指令
│   │   │   ├── extended/       # 扩展指令
│   │   │   └── reserved/       # 保留指令（native 方法调用）
│   │   ├── runtime/            # 运行时数据区
│   │   │   └── heap/           # 堆内存管理
│   │   ├── natives/            # Native 方法实现
│   │   │   ├── java/lang/      # java.lang 包的 native 方法
│   │   │   ├── java/io/        # java.io 包的 native 方法
│   │   │   └── sun/misc/       # sun.misc 包的 native 方法
│   │   ├── Bootstrap.java      # 主入口
│   │   ├── Command.java        # 命令行解析
│   │   ├── Classpath.java      # 类路径管理
│   │   ├── Constants.java      # JVM 常量定义
│   │   ├── Entry.java          # Entry 抽象类
│   │   └── Interpreter.java    # 字节码解释器
│   └── test/java/cn/abelib/javavm/  # 测试类
├── datas/                      # 测试数据
├── pom.xml                     # Maven 配置
└── README.md                   # 项目文档
```

## 贡献指南

欢迎提交 Issue 和 Pull Request 来改进这个项目！

## 许可证

见 LICENSE 文件

## 参考资料

- [The Java Virtual Machine Specification](https://docs.oracle.com/javase/specs/jvms/se8/html/)
- [深入理解 Java 虚拟机](https://book.douban.com/subject/24722612/)
- [自己动手写 Java 虚拟机](https://book.douban.com/subject/26742274/)
