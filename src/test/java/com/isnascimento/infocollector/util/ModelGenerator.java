package com.isnascimento.infocollector.util;

import com.isnascimento.infocollector.persistence.model.Article;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.Period;

@Component
public class ModelGenerator {

    public Article createValidArticle() {
        Article article = new Article("https://www.infomoney.com.br/mercados/moticia-destaque/");
        article.setTitle("Notícia destaque da semana");
        article.setSubtitle("Destaques da semana");
        article.setAuthor("InfoMoney");
        article.setDateTime(LocalDateTime.now());
        article.setContent("Este é o conteúdo da notícia do destaque da semana....");
        return article;
    }

    public Article createValidArticle2() {
        Article article = new Article("https://www.infomoney.com.br/mercados/a-grande-venda/");
        article.setTitle("Venda de uma das maiores empresas do país deve acontecer essa semana");
        article.setSubtitle("Venda de empresas cresce cada vez mais.");
        article.setAuthor("Mestre Yoda");
        article.setDateTime(LocalDateTime.now().minus(Period.ofDays(1)));
        article.setContent("Conteúdo da notícia de venda de empresa por Mestre Yoda....");
        return article;
    }

    public Article createValidArticle3() {
        Article article = new Article("https://www.infomoney.com.br/mercados/a-grande-venda/");
        article.setTitle("Empresas em TI crescem em 2020");
        article.setSubtitle("Crescimento do ano.");
        article.setAuthor("O cara");
        article.setDateTime(LocalDateTime.now().minus(Period.ofDays(5)));
        article.setContent("Empresas sempre crescem quando bem administradas....");
        return article;
    }

    public Article expectedInfoMoneyArticle() {
        Article article = new Article("https://www.infomoney.com.br/minhas-financas/monte-bravo-anuncia-tenista-bruno-soares-como-embaixador-da-marca/");
        article.setTitle("Monte Bravo anuncia tenista Bruno Soares como embaixador da marca");
        article.setSubtitle("Empresa tem como objetivo criar identidade baseada na resiliência e dedicação; grande projeto do atleta para o ano são as Olimpíadas de Tóquio");
        article.setAuthor("MoneyLab");
        article.setDateTime(OffsetDateTime.parse("2021-05-20T16:48:54-03:00").toLocalDateTime());
        article.setContent("A Monte Bravo, principal empresa de assessoria de investimentos credenciada à XP, acaba de anunciar o tenista Bruno Soares como embaixador da marca. A parceria, que envolve um projeto de longo prazo, consistirá no apoio em competições como as Olimpíadas de Tóquio, tendo como contrapartida a exposição da marca na mídia.\nDe acordo com a sócia e head de marketing da Monte Bravo, Rebeca Nevares, a sinergia com o atleta foi fundamental para o acordo. “Temos muita honra em ter o Bruno como nosso embaixador. Por ser atleta, ele é um exemplo de disciplina e resiliência, valores que cultivamos dentro da Monte Bravo. Queremos passar a ideia de que assim como no mercado financeiro, os esportes também oferecem momentos de volatilidade e pressão”, explica.\nNascido em Minas Gerais, Bruno Soares figura hoje entre os principais nomes do tênis brasileiro. Entre outros, já ganhou dois US Open e foi vice-campeão de Roland Garros em 2020. Segundo o atleta, a parceria vai além de um simples acordo comercial. “A Monte Bravo é a instituição que administra a minha saúde financeira, todos os recursos que eu conquistei durante a minha carreira. Me juntar a eles neste projeto é uma progressão natural, de muita confiança e orgulho”.\n\n\nRecomendado para você\nAs 10 melhores corretoras do Brasil para investir em ações\n\nRecomendado para você\nCurso gratuito: Pamela Semezzato explica como conseguiu extrair da Bolsa em um mês o que ganhava em um ano em seu antigo emprego\n\nRecomendado para você\nFliper oferece consolidador de informes de rendimento e facilita declaração do IR\nA iniciativa tem como objetivo, mais do que divulgar e dar um rosto à marca, criar identificação entre o público e a empresa por meio de sua missão e valores. Segundo Rebeca, a Monte Bravo preza por relações duradouras com seus clientes.\n“O Bruno Soares é a prova disso em um relacionamento que já dura há alguns anos. Além disso, queremos passar a ideia de que pessoas de sucesso precisam contar com os melhores profissionais para ajudar a gerenciar questões importantes da vida, como a financeira. Aqui nós trabalhamos muito com a ideia de que é possível realizar sonhos grandes com as pessoas certas”, finaliza.\nSobre a Monte Bravo\nPrincipal assessoria de investimentos do Brasil, a Monte Bravo hoje conta com mais de 20 mil clientes e cerca de R$17 bilhões sob assessoria. A empresa hoje conta com escritórios nas capitais de São Paulo, Porto Alegre, Rio de Janeiro, Goiânia, Minas Gerais (BH), Brasília (DF). São mais de 400 profissionais capacitados para auxiliar clientes com produtos financeiros que vão de ações, derivativos, títulos públicos até investimentos alternativos e soluções de wealth management.\n\nRelacionados\n");
        return article;
    }

}
