<template>
<<<<<<< Updated upstream
  <router-view/>
</template>

<style lang="scss">

</style>
=======
  <div>
    <!-- 머리말 -->
    <HeaderCom />

    <!-- 본문 -->
    <div class="container">
      <router-view />
    </div>
  </div>
</template>

<script>
import HeaderCom from "@/components/HeaderCom.vue"

export default {
  components: {
    HeaderCom
  }
}
</script>

<style lang="scss"></style>
>>>>>>> Stashed changes
