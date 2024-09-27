
# Aplikasi Nottes untuk Technical Test untuk PRIEDS #

Dibuat dengan menggunakan MVVM, dengan 3 bagian, yaitu:


1. Model: Yang berisikan _class_ NotesModel guna merepresentasikan data Catatan.
2. View:
- _MainActivity_: Menampilkan daftar catatan, menambah, mengedit dan menghapus catatan yang ada.
- _AddNotes_: Berguna untuk menambah dan mengedit catatan.
3. ViewModel:
- _NotesConnector_: Berguna untuk menyediakan / mengirim data ke _MainActivity_ dan mengelola operasi data menggunakan _notesDB_.
- _notesDB_: Berguna untuk operasi database, yang diakses melalui databaseConnector.

## Struktur Proyek ##
```
app/src/main/java/com/daniel/notesfortechnicalnotes
|
├── adapter/
│   └── NotesAdapter.java
│
├── dbConnector/
│   ├── NotesConnector.java
│   ├── notesDB.java
│   ├── databaseConnector.java
│
├── model/
│   └── NotesModel.java
│
├── AddNotes.java
└── MainActivity.java
```

## Cara Kerja ##
1. Membuat Notes Baru: Untuk menambahkan notes baru, pengguna dapat menekan button "+" yang ada di bagian kanan bawah. Setelah itu pengguna akan diarahkan ke halaman baru yang berisikan _field_ Title Notes dan Isi Notes, isikan keduanya dan tekan tombol "Save"
2. Mengedit Notes: Untuk mengubah isi konten notes, pilih salah satu dari notes yang ada, _pop-up dialog_ akan muncul dengan 2 pilihan berbeda, _"Edit"_ dan _"Delete"_. Pilih _"Edit"_ dan pengguna akan diarahkan halaman edit notes.
3. Menghapus Notes: Dengan metode yang sama seperti sebelumnya, pilih opsi _"Delete"_ lalu dialog konfirmasi _Yes or No_ akan muncul, pilih _Yes_. Notes akan dihapus.

## Dependensi / Plugin ##
Menggunakan beberapa dependensi utama yaitu:
1. Android Lifecycle Components: untuk menggunakan ViewModel dan LiveData guna tampilan live data.
2. RecyclerView: untuk menampilkan isi semua catatan dalam bentuk RecyclerView.
3. Material Components: untuk menampilkan dialog dan tombol tambah etc.

```
    implementation 'androidx.recyclerview:recyclerview:1.2.1'
    implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1'
    implementation 'androidx.lifecycle:lifecycle-livedata-ktx:2.5.1'
    implementation 'com.google.android.material:material:1.6.0'
```
