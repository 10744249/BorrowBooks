<template>
  <div class="books">
    <h2>書籍列表</h2>
    <ul>
      <li v-for="book in books" :key="book.id">
        {{ book.name }} - {{ book.author }}
        <button v-if="book.status === '可借閱'" @click="borrowBook(book.id)">借書</button>
        <button v-if="book.status === '借出'" @click="returnBook(book.id)">還書</button>
      </li>
    </ul>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      books: []
    };
  },
  created() {
    this.fetchBooks();
  },
  methods: {
    fetchBooks() {
      axios.get('/api/books')
          .then(response => {
            this.books = response.data;
          })
          .catch(error => {
            console.error("獲取書籍列表失敗:", error);
          });
    },
    borrowBook(id) {
      axios.post('/api/borrow', {userId: this.$store.state.userId, inventoryId: id})
          .then(() => {
            alert("借書成功！");
            this.fetchBooks(); // 重新獲取書籍列表以更新狀態
          })
          .catch(error => {
            alert("借書失敗：" + error.response.data.message);
          });
    },
    returnBook(id) {
      axios.post('/api/return', {userId: this.$store.state.userId, inventoryId: id})
          .then(() => {
            alert("還書成功！");
            this.fetchBooks(); // 重新獲取書籍列表以更新狀態
          })
          .catch(error => {
            alert("還書失敗：" + error.response.data.message);
          });
    }
  }
};
</script>

<style>
/* 添加您的樣式 */
.books ul {
  list-style-type: none;
  padding: 0;
}

.books li {
  margin: 10px 0;
  padding: 10px;
  background-color: #f0f0f0;
}
</style>
