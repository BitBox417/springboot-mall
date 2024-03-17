import { initializeApp } from "https://www.gstatic.com/firebasejs/10.8.1/firebase-app.js";
import {
  getStorage,
  ref,
  uploadBytesResumable,
  getDownloadURL,
} from "https://www.gstatic.com/firebasejs/10.8.1/firebase-storage.js";

const firebaseConfig = {
  apiKey: "AIzaSyAgibG8BzIrM3ZYiuK2yosHtS-k82ooYtM",
  authDomain: "product-f9d68.firebaseapp.com",
  projectId: "product-f9d68",
  storageBucket: "product-f9d68.appspot.com",
  messagingSenderId: "889632422011",
  appId: "1:889632422011:web:c33a6b412337eb807f4ade",
  measurementId: "G-X141S5FEMB",
};

// 初始化 Firebase 應用程式
const app = initializeApp(firebaseConfig);
// 獲取 Firebase Storage 實例
const storage = getStorage(app);

// 定義一個函式用於上傳圖片到 Firebase Storage
export const uploadImageToFirebase = function (file) {
  return new Promise((resolve, reject) => {
    // 創建一個 Storage 引用，指定上傳的檔案路徑
    const storageRef = ref(storage, `images/${file.name}`);
    // 開始上傳檔案
    const uploadTask = uploadBytesResumable(storageRef, file);
    // 監聽上傳進度和狀態
    uploadTask.on(
      "state_changed",
      (snapshot) => {
        // 計算上傳進度百分比
        const progress =
          (snapshot.bytesTransferred / snapshot.totalBytes) * 100;
        console.log("Upload is " + progress + "% done");
      },
      (error) => {
        // 處理上傳過程中的錯誤
        reject(error);
      },
      () => {
        // 上傳完成後獲取圖片的下載 URL
        getDownloadURL(uploadTask.snapshot.ref).then((downloadURL) => {
          resolve(downloadURL);
        });
      }
    );
  });
};
