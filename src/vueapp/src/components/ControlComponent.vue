<template>
    <div class="control-container">
        <b-button id="newButton" v-b-modal.register-news>Cadastrar</b-button>

        <b-modal id="register-news" size="xl" centered title="Cadastrar notícia"
            @show="resetModal" @hidden="resetModal" @ok="handleOk">
            <form ref="form" @submit.stop.prevent="handleSubmit">
                <b-form-group label="URL" label-for="url" :state="formState" :invalid-feedback="invalidFormMessage">
                    <b-form-input  id="url" v-model="url" :state="formState"></b-form-input>
                </b-form-group>
            </form>
        </b-modal>
        
        <div class="search-block">
            <form @submit.prevent="filterNews">
                <label for="search-input">Filtrar por título</label>
                <b-input id="search-input" class="search-input" v-model="filterString" />
            </form>
        </div>
    </div>
</template>

<script>
import { mapActions } from 'vuex'

export default {
    name: 'ControlComponent',

    data() {
        return {
            url: '',
            formState: null,
            invalidFormMessage: '',
            filterString: ''
        }
    },

    methods: {
        ...mapActions(['actionCollectNew']),
        ...mapActions(['actionFilterNewsByTitle']),

        validateModalForm() {
            if (this.url === '') {
                this.invalidFormMessage = 'O campo URL é obrigatório.'
                this.formState = false
                return false
            }
            try {
                new URL(this.url)
            } catch (e) {
                this.invalidFormMessage = 'A URL informada é inválida.'
                this.formState = false
                return false
            }
            if (!this.url.startsWith('https://www.infomoney.com.br/')) {
                this.invalidFormMessage = 'A o site da URL informada não é suportado.'
                this.formState = false
                return false
            }
            return true
        },
        handleOk(modalEvt) {
            modalEvt.preventDefault()
            this.handleSubmit()
        },
        async handleSubmit() {
            if (!this.validateModalForm()) {
                return
            }
            const response = await this.actionCollectNew(this.url)
            if (!response.ok) {
                this.$toast.error(response.data.message ? response.data.message : 'Ocorreu um erro interno, entre em contato com o administraodr do sistema.')
                return
            }
            this.$root.$emit('update-news');
            this.$nextTick(() => {
                    this.$bvModal.hide('register-news')
                })
            this.$toast.success('Notícia cadastrada com sucesso.')
        },
        resetModal() {
            this.url = ''
            this.formState = null
        },
        async filterNews() {
            await this.actionFilterNewsByTitle(this.filterString)
            this.$root.$emit('update-news');
        }
    }
}
</script>


<style lang="scss">
    .control-container {
        padding-left: 2rem;
        padding-right: 2rem;
        padding-bottom: 0.6rem;
    }
    .search-block {
        display: inline;
        float: right;

        .search-input {
            margin-left: 0.5rem;
            display: inline;
            width: auto;
        }
    }

</style>