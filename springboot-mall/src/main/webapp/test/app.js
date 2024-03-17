import { uploadImageToFirebase } from "./firebase.js";

const { createApp, ref, onMounted } = Vue;

const app = createApp({
  setup() {
    const searchQuery = ref({
      category: "",
      search: "",
    });

    const products = ref([]);
    const form = ref({
      productName: "",
      category: "",
      price: 0,
      stock: 0,
      description: "",
    });

    const file = ref(null);

    const isEditing = ref(false);
    const editingId = ref(null);

    const fetchProducts = async () => {
      try {
        const params = new URLSearchParams({
          category: searchQuery.value.category,
          search: searchQuery.value.search,
          orderBy: "created_date",
          sort: "desc",
          limit: 50,
          offset: 0,
        }).toString();
        const response = await axios.get(
          `http://localhost:8080/products?${params}`
        );
        products.value = response.data;
      } catch (error) {
        console.error(error);
      }
    };

    const deleteProduct = async (id) => {
      try {
        await axios.delete(`http://localhost:8080/products/${id}`);
        fetchProducts();
        Swal.fire({
          icon: "success",
          title: "成功",
          text: "商品已成功刪除",
          showConfirmButton: false,
          timer: 1500,
        });
      } catch (error) {
        console.error(error);
        Swal.fire({
          icon: "error",
          title: "錯誤",
          text: "刪除商品時發生錯誤",
        });
      }
    };

    const addProduct = async () => {
      const { value: formValues, isDismissed } = await Swal.fire({
        title: "新增商品",
        html:
          '<input id="swal-input1" class="swal2-input" placeholder="商品名稱">' +
          '<input id="swal-input2" class="swal2-input" placeholder="商品類別">' +
          '<input id="swal-input3" type="number" class="swal2-input" placeholder="商品價格">' +
          '<input id="swal-input4" type="number" class="swal2-input" placeholder="庫存數量">' +
          '<textarea id="swal-input5" class="swal2-textarea" placeholder="商品描述"></textarea>' +
          '<input id="swal-input6" type="file" class="swal2-input">',
        focusConfirm: false,
        showCancelButton: true,
        cancelButtonText: "關閉",
        preConfirm: () => {
          return [
            document.getElementById("swal-input1").value,
            document.getElementById("swal-input2").value,
            document.getElementById("swal-input3").value,
            document.getElementById("swal-input4").value,
            document.getElementById("swal-input5").value,
            document.getElementById("swal-input6").files[0],
          ];
        },
      });

      if (formValues && !isDismissed) {
        form.value.productName = formValues[0];
        form.value.category = formValues[1];
        form.value.price = formValues[2];
        form.value.stock = formValues[3];
        form.value.description = formValues[4];
        file.value = formValues[5];
        isEditing.value = false;
        submitForm();
      }
    };

    const editProduct = async (product) => {
      const { value: formValues, isDismissed } = await Swal.fire({
        title: "編輯商品",
        html:
          '<input id="swal-input1" class="swal2-input" placeholder="商品名稱" value="' +
          product.productName +
          '">' +
          '<input id="swal-input2" class="swal2-input" placeholder="商品類別" value="' +
          product.category +
          '">' +
          '<input id="swal-input3" type="number" class="swal2-input" placeholder="商品價格" value="' +
          product.price +
          '">' +
          '<input id="swal-input4" type="number" class="swal2-input" placeholder="庫存數量" value="' +
          product.stock +
          '">' +
          '<textarea id="swal-input5" class="swal2-textarea" placeholder="商品描述">' +
          product.description +
          "</textarea>" +
          '<input id="swal-input6" type="file" class="swal2-input">',
        focusConfirm: false,
        showCancelButton: true,
        cancelButtonText: "關閉",
        preConfirm: () => {
          return [
            document.getElementById("swal-input1").value,
            document.getElementById("swal-input2").value,
            document.getElementById("swal-input3").value,
            document.getElementById("swal-input4").value,
            document.getElementById("swal-input5").value,
            document.getElementById("swal-input6").files[0],
          ];
        },
      });

      if (formValues && !isDismissed) {
        form.value.productName = formValues[0];
        form.value.category = formValues[1];
        form.value.price = formValues[2];
        form.value.stock = formValues[3];
        form.value.description = formValues[4];
        file.value = formValues[5];
        form.value.imageUrl = product.imageUrl; // 將原有的圖片 URL 賦值給 form
        isEditing.value = true;
        editingId.value = product.productId;
        submitForm();
      }
    };

    const handleFileChange = (event) => {
      file.value = event.target.files[0];
    };

    const submitForm = async () => {
      if (file.value) {
        try {
          const imageUrl = await uploadImageToFirebase(file.value);
          form.value.imageUrl = imageUrl;
        } catch (error) {
          console.error("Error uploading image:", error);
          return;
        }
      }

      try {
        if (isEditing.value) {
          await axios.put(
            `http://localhost:8080/products/${editingId.value}`,
            form.value
          );
        } else {
          await axios.post("http://localhost:8080/products", form.value);
        }
        Swal.fire({
          icon: "success",
          title: "成功",
          text: isEditing.value ? "商品已成功更新" : "商品已成功新增",
          showConfirmButton: false,
          timer: 1500,
        });
        fetchProducts();
        form.value = {
          productName: "",
          category: "",
          price: 0,
          stock: 0,
          description: "",
        };
        isEditing.value = false;
        editingId.value = null;
        file.value = null;
      } catch (error) {
        console.error(error);
        Swal.fire({
          icon: "error",
          title: "錯誤",
          text: "提交表單時發生錯誤",
        });
      }
    };

    onMounted(() => {
      fetchProducts();
    });

    return {
      products,
      deleteProduct,
      editProduct,
      submitForm,
      form,
      handleFileChange,
      isEditing,
      searchQuery,
      fetchProducts,
      addProduct,
    };
  },
});

app.mount("#app");
