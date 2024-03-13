<template>
  <div class="register">
    <h2>註冊</h2>
    <form @submit.prevent="onRegister">
      <div>
        <label for="phoneNumber">手機號碼:</label>
        <input v-model="user.phoneNumber" type="text" id="phoneNumber" required />
      </div>
      <div>
        <label for="password">密碼:</label>
        <input v-model="user.password" type="password" id="password" required />
      </div>
      <div>
        <label for="confirmPassword">確認密碼:</label>
        <input v-model="confirmPassword" type="password" id="confirmPassword" required />
      </div>
      <button type="submit">註冊</button>
    </form>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      user: {
        phoneNumber: '',
        password: '',
      },
      confirmPassword: '',
    };
  },
  methods: {
    onRegister() {
      if (this.user.password !== this.confirmPassword) {
        alert("密碼和確認密碼不匹配");
        return;
      }

      axios.post('/api/register', this.user)
          .then(response => {
            alert("註冊成功");
            this.$router.push('/login');
          })
          .catch(error => {
            alert("註冊失敗: " + error.response.data);
          });
    },
  },
};
</script>

<style>
/* Add your styling here */
</style>
