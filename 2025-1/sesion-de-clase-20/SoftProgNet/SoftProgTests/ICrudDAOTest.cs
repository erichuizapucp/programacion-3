namespace PUCP.Edu.Pe.SoftProg.Tests.Persistencia {
    public interface ICrudDAOTest {
        void Test1DebeInsertar();
        void Test2DebeModificarSiIdExiste();
        void Test3NoDebeModificarSiIdNoExiste();
        void Test4NoDebeEliminarSiIdNoExiste();
        void Test5DebeEncontrarSiIdExiste();
        void Test6NoDebeEncontrarSiIdNoExiste();
        void Test7DebeListar();
        void Test8DebeEliminarSiIdExiste();
    }
}