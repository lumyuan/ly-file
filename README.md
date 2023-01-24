# AndroidExplorer
* This is a file operation library specially designed for Android. You can use it to access files in any directory on Android4.4~Android13, and even the coding method is very similar to java.io.File, which has low learning cost.

## How to
Add it in your root build.gradle at the end of repositories:
#### Step 1. Add the JitPack repository to your build file
```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
#### Step 2. Add the dependency
```gradle
dependencies {
	implementation 'com.github.lumyuan:ly-file:v0.0.1'
}
```

## How to usage
1. Initialization library
in your's application
```java
onCreate(){
	...
	FileApplication.init(application);
}
```
2. Check permissions
if sdk >= 23 && sdk <= 29
```java
if (!Permissions.hasCallExternalStorage()){
	Permissions.getCallExternalStorage(activity);
}
```
if sdk >= 30 && sdk <= 33
```java
if (!Permissions.hasCallAllFile()){
	Permissions.getCallAllFile();
}
```
if sdk >= 30 && sdk <= 33 AND target path belongs to a part of '/Android/data' or '/Android/obb'
```java
String targetPath = "/storage/emulated/0/Android/data/xxxxx";
String docId = Permissions.getDocId(targetPath);
String packageName = DocumentUtil.getPackageName(targetPath);
if (DocumentUtil.isPreviewDir(targetPath) && !Permissions.hasPrevDir(docId, packageName)){
	Permissions.getPrevDir(new Activity(), DocumentUtil.REQ_SAF_R_DATA, docId);
}

@Override
protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
	super.onActivityResult(requestCode, resultCode, data);
	Permissions.savePermissions(this, requestCode, resultCode, data);
}
```
3. Start use the File（The usage is similar to java.io.File）
```java
File file = new File(path);

//get file name
file.getName();

//get list absolute path
String[] list = file.list();
File[] listFile = file.listFile();

more...

//open I/O stream
//inputstream
try {
	InputStream inputStream = IOUtils.openInputStream(path);
}catch(IOException e){}
//outputstream
try {
	OutputStream outputStream = IOUtils.openOutputStream(path);
}catch(IOException e){}
```
4. some api
```java
//sort
FileSort.byName(File[] files, @OrderBy int orderBy)
FileSort.byDate(File[] files, @OrderBy int orderBy)
FileSort.byLength(File[] files, @OrderBy int orderBy)
```

# Last
Welcome star and push!
[![](https://jitpack.io/v/lumyuan/ly-file.svg)](https://jitpack.io/#lumyuan/ly-file)
