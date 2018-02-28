package org.aalin.common.ip.util;

import org.aalin.common.ip.exception.FileOperateException;
import org.springframework.util.Assert;

import java.io.*;
import java.util.*;

/**
 * IO工具。
 */
public abstract class IOUtils {

    /**
     * 缓冲区大小
     */
    public static final int BUFFER_SIZE = 4096;

    /**
     * 删除此路径名表示的文件或目录。 如果此路径名表示一个目录，则会先删除目录下的内容再将目录删除，所以该操作不是原子性的。
     * 如果目录中还有目录，则会引发递归动作。
     *
     * @param filePath 要删除文件或目录的路径。
     * @return 当且仅当成功删除文件或目录时，返回 true；否则返回 false。
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        return deleteFile(file);
    }

    private static boolean deleteFile(File file) {
        File[] files = file.listFiles();
        if (files != null) {
            for (File deleteFile : files) {
                if (deleteFile.isDirectory()) {
                    // 如果是文件夹，则递归删除下面的文件后再删除该文件夹
                    if (!deleteFile(deleteFile)) {
                        // 如果失败则返回
                        return false;
                    }
                } else {
                    if (!deleteFile.delete()) {
                        // 如果失败则返回
                        return false;
                    }
                }
            }
        }
        return file.delete();
    }

    /**
     * 在虚拟机终止时，请求删除此路径名表示的文件或目录。仅在虚拟机正常终止时，才会试图执行删除操作，这在 Java Language
     * Specification 中已定义。
     *
     * @param filePath 要删除文件或目录的路径。
     * @see File#deleteOnExit()
     */
    public static void deleteFileOnExit(String filePath) {
        File file = new File(filePath);
        file.deleteOnExit();
    }

    /**
     * 测试此路径名表示的文件或目录是否存在。
     *
     * @param filePath 要测试的文件或目录路径。
     * @return 当且仅当路径表示的文件或目录存在时，返回 true；否则返回 false。
     */
    public static boolean fileExists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * 测试此路径名表示的文件是否是一个目录。
     *
     * @param filePath 要测试是否为目录的路径。
     * @return 当且仅当路径名表示的文件存在且 是一个目录时，才返回 true；否则返回 false。
     */
    public static boolean fileIsDirectory(String filePath) {
        File file = new File(filePath);
        return file.isDirectory();
    }

    /**
     * 返回一个抽象路径名数组，这些路径名表示路径名所表示目录中的文件。
     *
     * @param filePath 要查找所有文件的目录路径名。
     * @return 表示路径名所表示目录中的文件和目录的抽象路径名数组。如果目录为空，则数组也将为空。
     * @throws FileOperateException 如果抽象路径名不表示一个目录，或者发生错误。
     */
    public static File[] fileArray(String filePath) {
        return fileArray(filePath, (FileFilter) null);
    }

    /**
     * 返回表示路径名所表示目录中的文件和目录的抽象路径名数组，这些路径名满足特定过滤器。此方法的行为与
     * {@link #fileArray(String)} 方法相同，除了所返回数组中的路径名必须满足过滤器。如果给定 filter 为
     * null，则接受所有路径名。否则，当且仅当在路径名上调用过滤器的 {@link FileFilter#accept(File)} 方法返回
     * true 时，该路径名才满足过滤器。
     *
     * @param filePath   要查找所有文件的目录路径名。
     * @param fileFilter 文件过滤器。
     * @return 表示路径名所表示目录中的文件和目录的抽象路径名数组。如果目录为空，则数组也将为空。
     * @throws FileOperateException 如果抽象路径名不表示一个目录，或者发生错误。
     */
    public static File[] fileArray(String filePath, FileFilter fileFilter) {
        File file = new File(filePath);
        isDirectory(file);
        return file.listFiles(fileFilter);
    }

    /**
     * 返回一个抽象路径名{@link List}，这些路径名表示路径名所表示目录中的文件。
     *
     * @param filePath 要查找所有文件的目录路径名。
     * @return 表示路径名所表示目录中的文件和目录的抽象路径名{@link List}。如果目录为空，则{@link List}也将为空。
     * @throws FileOperateException 如果抽象路径名不表示一个目录，或者发生错误。
     */
    public static List<File> fileList(String filePath) {
        return fileList(filePath, (FileFilter) null);
    }

    /**
     * 返回表示路径名所表示目录中的文件和目录的抽象路径名{@link List}，这些路径名满足特定过滤器。此方法的行为与
     * {@link #fileArray(String)} 方法相同，除了所返回{@link List}中的路径名必须满足过滤器。如果给定 filter
     * 为 null，则接受所有路径名。否则，当且仅当在路径名上调用过滤器的 {@link FileFilter#accept(File)} 方法返回
     * true 时，该路径名才满足过滤器。
     *
     * @param filePath   要查找所有文件的目录路径名。
     * @param fileFilter 文件过滤器。
     * @return 表示路径名所表示目录中的文件和目录的抽象路径名{@link List}。如果目录为空，则{@link List}也将为空。
     * @throws FileOperateException 如果抽象路径名不表示一个目录，或者发生错误。
     */
    public static List<File> fileList(String filePath, FileFilter fileFilter) {
        return Arrays.asList(fileArray(filePath, fileFilter));
    }

    /**
     * 返回表示路径名所表示目录中的文件和目录的抽象路径名数组，这些路径名满足特定过滤器。此方法的行为与
     * {@link #fileArray(String)} 方法相同，除了所返回数组中的路径名必须满足过滤器。如果给定 filter 为
     * null，则接受所有路径名。否则，当且仅当在此抽象路径名和它所表示的目录中的文件名或目录名上调用过滤器的
     * {@link FilenameFilter#accept(File, String)} 方法返回 true 时，该路径名才满足过滤器。
     *
     * @param filePath       要查找所有文件的目录路径名。
     * @param fileNameFilter 文件名过滤器。
     * @return 表示路径名所表示目录中的文件和目录的抽象路径名数组。如果目录为空，则数组也将为空。
     * @throws FileOperateException 如果抽象路径名不表示一个目录，或者发生错误。
     */
    public static File[] fileArray(String filePath, FilenameFilter fileNameFilter) {
        File file = new File(filePath);
        isDirectory(file);
        return file.listFiles(fileNameFilter);
    }

    /**
     * 返回表示路径名所表示目录中的文件和目录的抽象路径名{@link List}，这些路径名满足特定过滤器。此方法的行为与
     * {@link #fileArray(String)} 方法相同，除了所返回数组中的路径名必须满足过滤器。如果给定 filter 为
     * null，则接受所有路径名。否则，当且仅当在此抽象路径名和它所表示的目录中的文件名或目录名上调用过滤器的
     * {@link FilenameFilter#accept(File, String)} 方法返回 true 时，该路径名才满足过滤器。
     *
     * @param filePath       要查找所有文件的目录路径名。
     * @param fileNameFilter 文件名过滤器。
     * @return 表示路径名所表示目录中的文件和目录的抽象路径名{@link List}。如果目录为空，则{@link List}也将为空。
     * @throws FileOperateException 如果抽象路径名不表示一个目录，或者发生错误。
     */
    public static List<File> fileList(String filePath, FilenameFilter fileNameFilter) {
        return Arrays.asList(fileArray(filePath, fileNameFilter));
    }

    /**
     * 创建路径名指定的目录。
     *
     * @param path 路径名。
     * @return 当且仅当已创建目录时，返回 true；否则返回 false 。
     */
    public static boolean mkdir(String path) {
        File file = new File(path);
        return file.mkdir();
    }

    /**
     * 创建路径名指定的目录，包括创建必需但不存在的父目录。注意，如果此操作失败，可能已成功创建了一些必需的父目录。
     *
     * @param path 路径名。
     * @return 当且仅当已创建该目录以及所有必需的父目录时，返回 true；否则返回 false 。
     */
    public static boolean mkdirs(String path) {
        File file = new File(path);
        return file.mkdirs();
    }

    /**
     * 返回由路径名表示的文件的长度。如果此路径名表示一个目录，则返回值是不确定的。
     *
     * @param filePath 路径名。
     * @return 此抽象路径名表示的文件的长度，以字节为单位，如果文件不存在，则返回 0L 。
     */
    public static long length(String filePath) {
        File file = new File(filePath);
        return file.length();
    }

    /**
     * 设置由路径名所指定的文件或目录的最后一次修改时间。
     *
     * @param filePath 路径名。
     * @param time     最后一次修改的时间，用该时间与历元（1970 年 1 月 1 日，00:00:00
     *                 GMT）的时间差来计算（以毫秒为单位）。
     * @return 当且仅当该操作成功时，返回 true；否则返回 false。
     * @throws IllegalArgumentException 如果该参数为负 。
     */
    public static boolean setLastModified(String filePath, long time) {
        File file = new File(filePath);
        return file.setLastModified(time);
    }

    /**
     * 设置由路径名所指定的文件或目录的最后一次修改时间。
     *
     * @param filePath 路径名。
     * @param time     最后一次修改的时间。
     * @return 当且仅当该操作成功时，返回 true；否则返回 false。
     * @throws IllegalArgumentException 如果该参数为负 。
     */
    public static boolean setLastModified(String filePath, Calendar time) {
        return setLastModified(filePath, time.getTimeInMillis());
    }

    /**
     * 设置由路径名所指定的文件或目录的最后一次修改时间。
     *
     * @param filePath 路径名。
     * @param time     最后一次修改的时间。
     * @return 当且仅当该操作成功时，返回 true；否则返回 false。
     * @throws IllegalArgumentException 如果该参数为负 。
     */
    public static boolean setLastModified(String filePath, Date time) {
        return setLastModified(filePath, time.getTime());
    }

    /**
     * 返回路径名表示的文件最后一次被修改的时间。
     *
     * @param filePath 路径名。
     * @return 表示文件最后一次被修改的时间的 long 值，用该时间与历元（1970 年 1 月 1 日，00:00:00
     * GMT）的时间差来计算此值（以毫秒为单位）。如果该文件不存在，或是发生 I/O 错误，则返回 0L 。
     */
    public static long lastModified(String filePath) {
        File file = new File(filePath);
        return file.lastModified();
    }

    /**
     * 返回路径名表示的文件最后一次被修改的时间。
     *
     * @param filePath 路径名。
     * @return 表示文件最后一次被修改的时间 Date , 如果该文件不存在，或是发生 I/O 错误，则 Date.getTime() 返回
     * 0L。
     */
    public static Date lastModifiedToDate(String filePath) {
        return new Date(lastModified(filePath));
    }

    /**
     * 返回路径名表示的文件最后一次被修改的时间。
     *
     * @param filePath 路径名。
     * @return 表示文件最后一次被修改的时间 Calendar , 如果该文件不存在，或是发生 I/O 错误，则
     * Calendar.getTimeInMillis() 返回 0L。
     */
    public static Calendar lastModifiedToCalendar(String filePath) {
        Calendar calender = Calendar.getInstance();
        calender.setTimeInMillis(lastModified(filePath));
        return calender;
    }

    /**
     * 测试路径名表示的文件是否是一个目录。
     *
     * @param filePath 路径名。
     * @return 当且仅当路径名表示的文件存在且 是一个目录时，才返回 true；否则返回 false 。
     */
    public static boolean isDirectory(String filePath) {
        File file = new File(filePath);
        return file.isDirectory();
    }

    /**
     * 测试路径名表示的文件或目录是否存在。
     *
     * @param filePath 路径名。
     * @return 当且仅当路径名表示的文件或目录存在时，返回 true；否则返回 false 。
     */
    public static boolean exists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * 测试路径名表示的文件是否是一个标准文件。如果该文件不是一个目录，此外还满足其他与系统有关的标准，则该文件是标准 文件。
     *
     * @param filePath 路径名。
     * @return 当且仅当路径名表示的文件存在且 是一个标准文件时，返回 true；否则返回 false 。
     */
    public static boolean isStandardFile(String filePath) {
        File file = new File(filePath);
        return file.isFile();
    }

    /**
     * 测试路径名指定的文件是否是一个隐藏文件。隐藏 的具体定义与系统有关。在 UNIX 系统上，如果文件名以句点字符 ('.')
     * 开头，则认为该文件被隐藏。在 Microsoft Windows 系统上，如果在文件系统中文件被标记为隐藏，则认为该文件被隐藏。
     *
     * @param filePath 路径名。
     * @return 当且仅当路径名表示的文件是根据基础平台约定被隐藏时，返回 true 。
     */
    public static boolean isHidden(String filePath) {
        File file = new File(filePath);
        return file.isHidden();
    }

    /**
     * 重新命名路径名表示的文件。
     * 此方法行为的许多方面都是与平台有关的：重命名操作无法将一个文件从一个文件系统移动到另一个文件系统，该操作可能不是原子的，
     * 如果已经存在具有目标抽象路径名的文件，则该操作可能无法获得成功。应该始终检查返回值，确保重命名操作成功。
     *
     * @param filePath   路径名。
     * @param toFilePath 新的命名的路径名。
     * @return 当且仅当重命名成功时，返回 true；否则返回 false 。
     */
    public static boolean renameTo(String filePath, String toFilePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return false;
        }
        File toFile = new File(toFilePath);
        return file.renameTo(toFile);
    }

    /**
     * 标记路径名指定的文件或目录，以便只可对其进行读操作。在调用此方法后，可以保证在被删除或被标记为允许写访问之前，文件或目录不会发生更改。
     * 是否可以删除某个只读文件或目录则取决于基础系统。
     *
     * @param filePath 路径名。
     * @return 当且仅当该操作成功时，返回 true；否则返回 false。
     */
    public static boolean setReadOnly(String filePath) {
        File file = new File(filePath);
        return file.setReadOnly();
    }

    private static void isDirectory(File file) {
        if (!file.isDirectory()) {
            throw new FileOperateException("所指定路径 [" + file.getPath() + "] 不是一个目录或者 不存在。");
        }
    }

    /**
     * 通过 路径 得到文件/文件夹扩展名。
     *
     * @param file 要得到扩展名文件/文件夹的路径。
     * @return file 对应的文件/文件夹的扩展名。
     * @throws IllegalArgumentException 当 file 为 null 时。
     */
    public static String getExtension(File file) {
        Assert.notNull(file);
        return getExtension(file.getPath());
    }

    /**
     * 通过 路径名 得到文件/文件夹扩展名。
     *
     * @param fileName 要得到扩展名文件/文件夹的路径名。
     * @return fileName 对应的文件/文件夹的扩展名。
     * @throws IllegalArgumentException 当 fileName 为 null 时。
     */
    public static String getExtension(String fileName) {
        Assert.notNull(fileName);
        int index = fileName.lastIndexOf('.');
        if (index == -1) {
            return "";
        }
        return fileName.substring(index + 1);
    }

    /**
     * 剪切操作，该操作相当于一次复制和一次删除，所以它不是原子性的。
     *
     * @param filePath   要剪切的路径名。
     * @param toFilePath 要剪切到的路径名。
     * @return 剪切的文件/文件夹大小。
     * @throws IOException 发生 I/O 错误时。
     */
    public static int cut(String filePath, String toFilePath) throws IOException {
        File file = new File(filePath);
        File toFile = new File(toFilePath);
        return cut(file, toFile);
    }

    /**
     * 剪切操作，该操作相当于一次复制和一次删除，所以它不是原子性的。
     *
     * @param filePath 要剪切的路径名。
     * @param toFile   要剪切到的路径。
     * @return 剪切的文件/文件夹大小。
     * @throws IOException 发生 I/O 错误时。
     */
    public static int cut(String filePath, File toFile) throws IOException {
        File file = new File(filePath);
        return cut(file, toFile);
    }

    /**
     * 剪切操作，该操作相当于一次复制和一次删除，所以它不是原子性的。
     *
     * @param file       要剪切的路径。
     * @param toFilePath 要剪切到的路径名。
     * @return 剪切的文件/文件夹大小。
     * @throws IOException 发生 I/O 错误时。
     */
    public static int cut(File file, String toFilePath) throws IOException {
        File toFile = new File(toFilePath);
        return cut(file, toFile);
    }

    /**
     * 剪切操作，该操作相当于一次复制和一次删除，所以它不是原子性的。
     *
     * @param file   要剪切的路径。
     * @param toFile 要剪切到的路径。
     * @return 剪切的文件/文件夹大小。
     * @throws IOException 发生 I/O 错误时。
     */
    public static int cut(File file, File toFile) throws IOException {
        Queue<File> copyFileQueue = new ArrayQueue<File>();
        try {
            int byteCount = copy(file, toFile, 0, copyFileQueue);
            boolean deleteOutcome = deleteFile(file);
            if (!deleteOutcome) {
                // 如果删除之前的文件失败，则删除复制过去的文件
                for (File deleteFile : copyFileQueue) {
                    deleteFile.delete();
                }
            }
            return byteCount;
        } catch (IOException e) {
            // 如果在复制时发生错误，则删除未完成的文件
            for (File deleteFile : copyFileQueue) {
                deleteFile.delete();
            }
            throw e;
        }
    }

    /**
     * 将输入流的内容使用输出流写出。
     *
     * @param in       输入流。
     * @param out      输出流。
     * @param closeIn  如果为 true 将调用 {@link InputStream#close()} 。
     * @param closeOut 如果为 true 将调用 {@link OutputStream#close()} 。
     * @return 操作的数据大小。
     * @throws IOException 发生 I/O 错误时。
     */
    public static int copy(InputStream in, OutputStream out, boolean closeIn, boolean closeOut) throws IOException {
        try {
            int byteCount = 0;
            byte[] buffer = new byte[BUFFER_SIZE];
            for (int bytesRead = -1; (bytesRead = in.read(buffer)) != -1; ) {
                out.write(buffer, 0, bytesRead);
                byteCount += bytesRead;
            }
            out.flush();
            return byteCount;
        } finally {
            try {
                in.close();
            } catch (IOException e) {

            }
            try {
                out.close();
            } catch (IOException e) {

            }
        }
    }

    /**
     * 将输入流的内容使用输出流写出。
     * <p>
     * <p>
     * 该操作不会对 in 和 out 所对应的流进行关闭。
     *
     * @param in  输入流。
     * @param out 输出流。
     * @return 操作的数据大小。
     * @throws IOException 发生 I/O 错误时。
     */
    public static int copy(InputStream in, OutputStream out) throws IOException {
        return copy(in, out, false, false);
    }

    /**
     * 将 文件/文件夹 的内容复制到指定的 文件/文件夹 路径。
     *
     * @param in  要复制的文件/文件夹路径。
     * @param out 要复制到文件/文件夹路径。
     * @return 复制的文件/文件夹大小。
     * @throws IOException 发生 I/O 错误时。
     */
    public static int copy(File in, File out) throws IOException {
        return copy(in, out, 0, null);
    }

    private static int copy(File in, File out, int byteCount, Queue<File> copyFileQueue) throws IOException {
        File parent = out.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        if (in.isDirectory()) { // 为目录
            File[] files = in.listFiles();
            for (File file : files) {
                String newPath = out.getPath() + File.separator + file.getName();
                byteCount = byteCount + copy(file, new File(newPath), byteCount, copyFileQueue);
            }
            if (copyFileQueue != null) {
                copyFileQueue.offer(out);
            }
            return byteCount;
        } else { // 不为目录
            byteCount = byteCount
                    + copy(new BufferedInputStream(new FileInputStream(in)), new BufferedOutputStream(
                    new FileOutputStream(out)), true, true);
            if (copyFileQueue != null) {
                copyFileQueue.offer(out);
            }
            return byteCount;
        }
    }

    /**
     * 将 文件/文件夹 的内容复制到指定的 文件/文件夹 路径名。
     *
     * @param inFilePath  要复制的文件/文件夹路径名。
     * @param outFilePath 要复制到文件/文件夹路径名。
     * @return 复制的文件/文件夹大小。
     * @throws IOException 发生 I/O 错误时。
     */
    public static int copy(String inFilePath, String outFilePath) throws IOException {
        return copy(new File(inFilePath), new File(outFilePath));
    }

    /**
     * 将 文件/文件夹 的内容复制到指定的 文件/文件夹 路径。
     *
     * @param inFilePath 要复制的文件/文件夹路径名。
     * @param out        要复制到文件/文件夹路径。
     * @return 复制的文件/文件夹大小。
     * @throws IOException 发生 I/O 错误时。
     */
    public static int copy(String inFilePath, File out) throws IOException {
        return copy(new File(inFilePath), out);
    }

    /**
     * 将 文件/文件夹 的内容复制到指定的 文件/文件夹 路径名。
     *
     * @param in          要复制的文件/文件夹路径。
     * @param outFilePath 要复制到文件/文件夹路径名。
     * @return 复制的文件/文件夹大小。
     * @throws IOException 发生 I/O 错误时。
     */
    public static int copy(File in, String outFilePath) throws IOException {
        return copy(in, new File(outFilePath));
    }

    /**
     * 将字节数组的数据写进路径的文件。
     *
     * @param in  要写进文件的字节数组。
     * @param out 文件路径。
     * @throws IOException 发生 I/O 错误时。
     */
    public static void copy(byte[] in, File out) throws IOException {
        ByteArrayInputStream inStream = new ByteArrayInputStream(in);
        OutputStream outStream = new BufferedOutputStream(new FileOutputStream(out));
        copy(inStream, outStream, true, true);
    }

    /**
     * 将字节数组的数据写进路径名的文件。
     *
     * @param in          要写进文件的字节数组。
     * @param outFilePath 文件路径名。
     * @throws IOException 发生 I/O 错误时。
     */
    public static void copy(byte[] in, String outFilePath) throws IOException {
        copy(in, new File(outFilePath));
    }

    /**
     * 读取一个输入流的内容到字节数组。
     * <p>
     * <p>
     * 该操作不会关闭 in 所对应的流。
     *
     * @param in 要读取内容的输入流。
     * @return 输入流内容的字节数组。
     * @throws IOException 发生 I/O 错误时。
     */
    public static byte[] copyToByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream(BUFFER_SIZE);
        copy(in, out, false, true);
        return out.toByteArray();
    }

    /**
     * 将字节数组的数据写进一个输出流。
     *
     * @param in  要写进输出流的字节数组。
     * @param out 输出流。
     * @throws IOException 发生 I/O 错误时。
     */
    public static void copy(byte[] in, OutputStream out) throws IOException {
        try {
            out.write(in);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {

            }
        }
    }

    /**
     * 读取一个路径的内容到字节数组。
     *
     * @param in 路径。
     * @return 路径的数据对应的字节数组。
     * @throws IOException 发生 I/O 错误时。
     */
    public static byte[] copyToByteArray(File in) throws IOException {
        return copyToByteArray(new BufferedInputStream(new FileInputStream(in)));
    }

    /**
     * 读取一个路径名的内容到字节数组。
     *
     * @param inFilePath 路径名。
     * @return 路径名的数据对应的字节数组。
     * @throws IOException 发生 I/O 错误时。
     */
    public static byte[] copyToByteArray(String inFilePath) throws IOException {
        return copyToByteArray(new File(inFilePath));
    }
}
