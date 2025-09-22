using System.Collections.Generic;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using PUCP.SoftProg.Persistencia.DAO.RRHH;
using PUCP.SoftProg.Persistencia.DAOImpl.RRHH;
using PUCP.SoftProg.Modelo.RRHH;

namespace PUCP.SoftProg.Tests.Persistencia.RRHH {
    [TestClass]
    public class AreaDAOTest : IPersistibleTest{
        private static int testId;
        private readonly int idIncorrecto = 99999;

        [ClassInitialize]
        public static void ClassInitialize(TestContext context) {
            IAreaDAO areaDao = new AreaDAOImpl();
            var area = new Area {
                Nombre = "Area de Prueba",
                IsActive = true
            };

            testId = areaDao.Crear(area);
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
            
            bool modifico = areaDao.Actualizar(area);
            Assert.IsTrue(modifico);

            Area areaModificada = areaDao.Leer(testId);
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

            bool modifico = areaDao.Actualizar(area);
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
            Area area = areaDao.Leer(testId);
            Assert.IsNotNull(area);
        }

        [TestMethod]
        public void Test6NoDebeEncontrarSiIdNoExiste() {
            IAreaDAO areaDao = new AreaDAOImpl();
            Area area = areaDao.Leer(this.idIncorrecto);
            Assert.IsNull(area);
        }

        [TestMethod]
        public void Test7DebeListar() {
            IAreaDAO areaDao = new AreaDAOImpl();
            List<Area> areas = areaDao.LeerTodos();

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
