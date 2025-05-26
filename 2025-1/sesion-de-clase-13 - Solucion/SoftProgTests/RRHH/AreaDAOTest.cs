using System.Collections.Generic;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using PUCP.Edu.Pe.SoftProg.Persistencia.DAO.RRHH;
using PUCP.Edu.Pe.SoftProg.Persistencia.DAOImpl.RRHH;
using PUCP.Edu.Pe.SoftProg.Modelo.RRHH;

namespace PUCP.Edu.Pe.SoftProg.Tests.Persistencia.RRHH {
    [TestClass]
    public class AreaDAOTest : ICrudDAOTest{
        private static int testId;
        private readonly int idIncorrecto = 99999;

        [ClassInitialize]
        public static void ClassInitialize(TestContext context) {
            IAreaDAO areaDao = new AreaDAOImpl();
            var area = new Area {
                Nombre = "Area de Prueba",
                IsActive = true
            };

            testId = areaDao.Insertar(area);
        }

        [ClassCleanup]
        public static void ClassCleanUp() {
        }

        [TestMethod]
        public void Test1DebeInsertar() {
            Assert.IsTrue(testId > 0);
        }

        [TestMethod]
        public void Test2DebeModificarSiIdExiste() {
            IAreaDAO areaDao = new AreaDAOImpl();
            Area area = new Area {
                Id = testId, 
                Nombre = "Area de Prueba Modificada", 
                IsActive = false
            };
            
            bool modifico = areaDao.Modificar(area);
            Assert.IsTrue(modifico);

            Area areaModificada = areaDao.Buscar(testId);
            Assert.AreEqual(areaModificada.Nombre, "Area de Prueba Modificada");
            Assert.IsFalse(areaModificada.IsActive);
        }

        [TestMethod]
        public void Test3NoDebeModificarSiIdNoExiste() {
            IAreaDAO areaDao = new AreaDAOImpl();
            Area area = new Area {
                Id = this.idIncorrecto, 
                Nombre = "Area de Prueba Modificada", 
                IsActive = false
            };

            bool modifico = areaDao.Modificar(area);
            Assert.IsFalse(modifico);
        }

        [TestMethod]
        public void Test4NoDebeEliminarSiIdNoExiste() {
            IAreaDAO areaDao = new AreaDAOImpl();
            bool elimino = areaDao.Eliminar(this.idIncorrecto);
            Assert.IsFalse(elimino);
        }

        [TestMethod]
        public void Test5DebeEncontrarSiIdExiste() {
            IAreaDAO areaDao = new AreaDAOImpl();
            Area area = areaDao.Buscar(testId);
            Assert.IsNotNull(area);
        }

        [TestMethod]
        public void Test6NoDebeEncontrarSiIdNoExiste() {
            IAreaDAO areaDao = new AreaDAOImpl();
            Area area = areaDao.Buscar(this.idIncorrecto);
            Assert.IsNull(area);
        }

        [TestMethod]
        public void Test7DebeListar() {
            IAreaDAO areaDao = new AreaDAOImpl();
            List<Area> areas = areaDao.Listar();

            Assert.IsNotNull(areas);
            Assert.IsTrue(areas.Count > 0);
        }

        [TestMethod]
        public void Test8DebeEliminarSiIdExiste() {
            IAreaDAO areaDao = new AreaDAOImpl();
            bool elimino = areaDao.Eliminar(testId);
            Assert.IsTrue(elimino);
        }
    }
}
