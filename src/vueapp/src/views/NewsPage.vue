<template>
    <div>
        <NavBarComponent />
        <TitleComponent />
        <ControlComponent />
        <NewsTableComponent />
    </div>
</template>

<script>
import NavBarComponent from '@/components/NavBarComponent.vue'
import TitleComponent from '@/components/TitleComponent.vue'
import ControlComponent from '@/components/ControlComponent.vue'
import NewsTableComponent from '@/components/NewsTableComponent.vue'

export default {
    name: 'NewsPage',

    data() {
        return {
            
        }
    },

    components: {
        NavBarComponent,
        TitleComponent,
        ControlComponent,
        NewsTableComponent
    }
}
</script>

<style lang="scss">

</style>
