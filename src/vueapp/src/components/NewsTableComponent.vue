<template>
    <div class="table-container">
        <b-overlay :show="showLoading" rounded="sm">
            <b-table ref="newsTable" class="tableScroll no-span" borderedless :sticky-header="true"
                outlined hover selectable :items="items" :fields="fields" @row-selected="onRowSelected"></b-table>
        </b-overlay>

        <b-modal id="modal-scrollable" scrollable cancel-disabled size="xl" :title="this.modalItem.title" @hidden="clearSelection">
            <h6>{{ modalItem.subtitle }}</h6>
            <p>Postado por: {{ modalItem.author }}</p>
            <p>Ultima atualização: {{ modalItem.dateTime }}</p>
            <p>{{ modalItem.content }}</p>
            <p>Fonte: <a v-bind:href="modalItem.url" target="_blank">{{ modalItem.url }}</a></p>
            
            <template #modal-footer="{ ok }">
                <b-button size="md" variant="primary" @click="ok()">OK</b-button>
            </template>
        </b-modal>
    </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'

export default {
    name: 'NewsTableComponent',

    data() {
        return {
            showLoading: true,
            items: [],
            fields: [
                {
                    key: 'title',
                    sortable: true,
                    label: 'Título'
                },
                {
                    key: 'author',
                    sortable: true,
                    label: 'Autor'
                },
                {
                    key: 'dateTime',
                    sortable: true,
                    label: 'Data e hora'
                },
            ],
            modalItem: {}
        }
    },

    async created() {
        this.showLoading = true
        await this.actionGetNewslist()
        this.updateTable()
        this.showLoading = false
    },

    mounted() {
        this.$root.$on('update-news', () => {
            this.updateTable()
        });
    },

    computed: {
        ...mapGetters(['getNewsList'])
    },

    methods: {
        ...mapActions(['actionGetNewslist']),
        ...mapActions(['actionGetNewById']),

        updateTable() {
            this.items = this.getNewsList
        },
        async onRowSelected(item) {
            if (!item[0]) {
                return
            }
            const response = await this.actionGetNewById(item[0].id)
            this.modalItem = response.data
            this.$bvModal.show('modal-scrollable')
        },
        clearSelection() {
            this.$refs.newsTable.clearSelected()
        }
    }
}
</script>


<style lang="scss">
    .table-container {
        display: flex;
        flex-direction: column;
        height: 100%;
        padding-left: 2rem;
        padding-right: 2rem;

        .tableScroll {
            max-height: 60vh;
        }

        .no-span {
            span {
                display: none;
            }
        }
    }

</style>