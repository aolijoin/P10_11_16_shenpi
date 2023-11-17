<script>
import order from "@/components/order.vue";
import about from "@/components/about.vue";

export default {
  comments: {
    order,
    about
  }
}
</script>

<template>
  <div id="main">
    <nav>
      <router-link to="/order">单据管理</router-link>
      |
      <router-link to="/about">审批单管理</router-link>
    </nav>
  </div>
</template>

<style>

</style>