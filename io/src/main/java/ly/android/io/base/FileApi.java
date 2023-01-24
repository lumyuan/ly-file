package ly.android.io.base;

import java.io.IOException;
import java.nio.file.LinkOption;
import java.nio.file.Path;

public interface FileApi {
    /**
     * Tests whether the file or directory denoted by this abstract pathname
     * exists.
     *
     * @return <code>true</code> if and only if the file or directory denoted
     * by this abstract pathname exists; <code>false</code> otherwise
     * @throws SecurityException If a security manager exists and its <code>{@link
     *                           java.lang.SecurityManager#checkRead(java.lang.String)}</code>
     *                           method denies read access to the file or directory
     */
    public boolean exists();

    /**
     * Returns the name of the file or directory denoted by this abstract
     * pathname.  This is just the last name in the pathname's name
     * sequence.  If the pathname's name sequence is empty, then the empty
     * string is returned.
     *
     * @return The name of the file or directory denoted by this abstract
     * pathname, or the empty string if this pathname's name sequence
     * is empty
     */
    public String getName();

    /**
     * Returns the pathname string of this abstract pathname's parent, or
     * <code>null</code> if this pathname does not name a parent directory.
     *
     * <p> The <em>parent</em> of an abstract pathname consists of the
     * pathname's prefix, if any, and each name in the pathname's name
     * sequence except for the last.  If the name sequence is empty then
     * the pathname does not name a parent directory.
     *
     * @return The pathname string of the parent directory named by this
     * abstract pathname, or <code>null</code> if this pathname
     * does not name a parent
     */
    public String getParent();

    /**
     * Converts this abstract pathname into a pathname string.
     *
     * @return The string form of this abstract pathname
     */
    public String getPath();

    /**
     * Tests whether the application can read the file denoted by this
     * abstract pathname.
     *
     * @return <code>true</code> if and only if the file specified by this
     * abstract pathname exists <em>and</em> can be read by the
     * application; <code>false</code> otherwise
     * @throws SecurityException If a security manager exists and its <code>{@link
     *                           java.lang.SecurityManager#checkRead(java.lang.String)}</code>
     *                           method denies read access to the file
     */
    public boolean canRead();

    /**
     * Tests whether the application can modify the file denoted by this
     * abstract pathname.
     *
     * @return <code>true</code> if and only if the file system actually
     * contains a file denoted by this abstract pathname <em>and</em>
     * the application is allowed to write to the file;
     * <code>false</code> otherwise.
     * @throws SecurityException If a security manager exists and its <code>{@link
     *                           java.lang.SecurityManager#checkWrite(java.lang.String)}</code>
     *                           method denies write access to the file
     */
    public boolean canWrite();

    /**
     * Tests whether the file denoted by this abstract pathname is a
     * directory.
     *
     * <p> Where it is required to distinguish an I/O exception from the case
     * that the file is not a directory, or where several attributes of the
     * same file are required at the same time, then the {@link
     * java.nio.file.Files#readAttributes(Path, Class, LinkOption[])
     * Files.readAttributes} method may be used.
     *
     * @return <code>true</code> if and only if the file denoted by this
     * abstract pathname exists <em>and</em> is a directory;
     * <code>false</code> otherwise
     * @throws SecurityException If a security manager exists and its <code>{@link
     *                           java.lang.SecurityManager#checkRead(java.lang.String)}</code>
     *                           method denies read access to the file
     */
    public boolean isDirectory();

    /**
     * Tests whether the file denoted by this abstract pathname is a normal
     * file.  A file is <em>normal</em> if it is not a directory and, in
     * addition, satisfies other system-dependent criteria.  Any non-directory
     * file created by a Java application is guaranteed to be a normal file.
     *
     * <p> Where it is required to distinguish an I/O exception from the case
     * that the file is not a normal file, or where several attributes of the
     * same file are required at the same time, then the {@link
     * java.nio.file.Files#readAttributes(Path, Class, LinkOption[])
     * Files.readAttributes} method may be used.
     *
     * @return <code>true</code> if and only if the file denoted by this
     * abstract pathname exists <em>and</em> is a normal file;
     * <code>false</code> otherwise
     * @throws SecurityException If a security manager exists and its <code>{@link
     *                           java.lang.SecurityManager#checkRead(java.lang.String)}</code>
     *                           method denies read access to the file
     */
    public boolean isFile();

    /**
     * Returns the time that the file denoted by this abstract pathname was
     * last modified.
     *
     * <p> Where it is required to distinguish an I/O exception from the case
     * where {@code 0L} is returned, or where several attributes of the
     * same file are required at the same time, or where the time of last
     * access or the creation time are required, then the {@link
     * java.nio.file.Files#readAttributes(Path, Class, LinkOption[])
     * Files.readAttributes} method may be used.
     *
     * @return A <code>long</code> value representing the time the file was
     * last modified, measured in milliseconds since the epoch
     * (00:00:00 GMT, January 1, 1970), or <code>0L</code> if the
     * file does not exist or if an I/O error occurs
     * @throws SecurityException If a security manager exists and its <code>{@link
     *                           java.lang.SecurityManager#checkRead(java.lang.String)}</code>
     *                           method denies read access to the file
     */
    public long lastModified();

    /**
     * Returns the length of the file denoted by this abstract pathname.
     * The return value is unspecified if this pathname denotes a directory.
     *
     * <p> Where it is required to distinguish an I/O exception from the case
     * that {@code 0L} is returned, or where several attributes of the same file
     * are required at the same time, then the {@link
     * java.nio.file.Files#readAttributes(Path, Class, LinkOption[])
     * Files.readAttributes} method may be used.
     *
     * @return The length, in bytes, of the file denoted by this abstract
     * pathname, or <code>0L</code> if the file does not exist.  Some
     * operating systems may return <code>0L</code> for pathnames
     * denoting system-dependent entities such as devices or pipes.
     * @throws SecurityException If a security manager exists and its <code>{@link
     *                           java.lang.SecurityManager#checkRead(java.lang.String)}</code>
     *                           method denies read access to the file
     */
    public long length();

    /**
     * Atomically creates a new, empty file named by this abstract pathname if
     * and only if a file with this name does not yet exist.  The check for the
     * existence of the file and the creation of the file if it does not exist
     * are a single operation that is atomic with respect to all other
     * filesystem activities that might affect the file.
     * <p>
     * Note: this method should <i>not</i> be used for file-locking, as
     * the resulting protocol cannot be made to work reliably. The
     * {@link java.nio.channels.FileLock FileLock}
     * facility should be used instead.
     *
     * @return <code>true</code> if the named file does not exist and was
     * successfully created; <code>false</code> if the named file
     * already exists
     * @throws IOException       If an I/O error occurred
     * @throws SecurityException If a security manager exists and its <code>{@link
     *                           java.lang.SecurityManager#checkWrite(java.lang.String)}</code>
     *                           method denies write access to the file
     * @since 1.2
     */
    public boolean createNewFile() throws IOException;

    /**
     * Deletes the file or directory denoted by this abstract pathname.  If
     * this pathname denotes a directory, then the directory must be empty in
     * order to be deleted.
     *
     * <p> Note that the {@link java.nio.file.Files} class defines the {@link
     * java.nio.file.Files#delete(Path) delete} method to throw an {@link IOException}
     * when a file cannot be deleted. This is useful for error reporting and to
     * diagnose why a file cannot be deleted.
     *
     * @return <code>true</code> if and only if the file or directory is
     * successfully deleted; <code>false</code> otherwise
     * @throws SecurityException If a security manager exists and its <code>{@link
     *                           java.lang.SecurityManager#checkDelete}</code> method denies
     *                           delete access to the file
     */
    public boolean delete();

    /**
     * Returns an array of strings naming the files and directories in the
     * directory denoted by this abstract pathname.
     *
     * <p> If this abstract pathname does not denote a directory, then this
     * method returns {@code null}.  Otherwise an array of strings is
     * returned, one for each file or directory in the directory.  Names
     * denoting the directory itself and the directory's parent directory are
     * not included in the result.  Each string is a file name rather than a
     * complete path.
     *
     * <p> There is no guarantee that the name strings in the resulting array
     * will appear in any specific order; they are not, in particular,
     * guaranteed to appear in alphabetical order.
     *
     * <p> Note that the {@link java.nio.file.Files} class defines the {@link
     * java.nio.file.Files#newDirectoryStream(Path) newDirectoryStream} method to
     * open a directory and iterate over the names of the files in the directory.
     * This may use less resources when working with very large directories, and
     * may be more responsive when working with remote directories.
     *
     * @return An array of strings naming the files and directories in the
     * directory denoted by this abstract pathname.  The array will be
     * empty if the directory is empty.  Returns {@code null} if
     * this abstract pathname does not denote a directory, or if an
     * I/O error occurs.
     * @throws SecurityException If a security manager exists and its {@link
     *                           SecurityManager#checkRead(String)} method denies read access to
     *                           the directory
     */
    public String[] list();

    /**
     * Returns an array of strings naming the files and directories in the
     * directory denoted by this abstract pathname.
     *
     * <p> If this abstract pathname does not denote a directory, then this
     * method returns {@code null}.  Otherwise an array of strings is
     * returned, one for each file or directory in the directory.  Names
     * denoting the directory itself and the directory's parent directory are
     * not included in the result.  Each string is a file name rather than a
     * complete path.
     *
     * <p> There is no guarantee that the name strings in the resulting array
     * will appear in any specific order; they are not, in particular,
     * guaranteed to appear in alphabetical order.
     *
     * <p> Note that the {@link java.nio.file.Files} class defines the {@link
     * java.nio.file.Files#newDirectoryStream(Path) newDirectoryStream} method to
     * open a directory and iterate over the names of the files in the directory.
     * This may use less resources when working with very large directories, and
     * may be more responsive when working with remote directories.
     *
     * @return An array of strings naming the files and directories in the
     * directory denoted by this abstract pathname.  The array will be
     * empty if the directory is empty.  Returns {@code null} if
     * this abstract pathname does not denote a directory, or if an
     * I/O error occurs.
     * @throws SecurityException If a security manager exists and its {@link
     *                           SecurityManager#checkRead(String)} method denies read access to
     *                           the directory
     */
    public boolean mkdirs();

    // Android-changed: Replaced generic platform info with Android specific one.
    /**
     * Renames the file denoted by this abstract pathname.
     *
     * <p>Many failures are possible. Some of the more likely failures include:
     * <ul>
     * <li>Write permission is required on the directories containing both the source and
     * destination paths.
     * <li>Search permission is required for all parents of both paths.
     * <li>Both paths be on the same mount point. On Android, applications are most likely to hit
     * this restriction when attempting to copy between internal storage and an SD card.
     * </ul>
     *
     * <p>The return value should always be checked to make sure
     * that the rename operation was successful.
     *
     * <p> Note that the {@link java.nio.file.Files} class defines the {@link
     * java.nio.file.Files#move move} method to move or rename a file in a
     * platform independent manner.
     *
     * @param dest The new abstract pathname for the named file
     * @return <code>true</code> if and only if the renaming succeeded;
     * <code>false</code> otherwise
     * @throws SecurityException    If a security manager exists and its <code>{@link
     *                              java.lang.SecurityManager#checkWrite(java.lang.String)}</code>
     *                              method denies write access to either the old or new pathnames
     * @throws NullPointerException If parameter <code>dest</code> is <code>null</code>
     */
    public boolean renameTo(String dest);
}
